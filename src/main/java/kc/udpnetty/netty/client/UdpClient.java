package kc.udpnetty.netty.client;

import io.netty.handler.timeout.IdleStateHandler;
import kc.udpnetty.bean.HeartbeatMessage;
import kc.udpnetty.bean.KeepSendMessage;
import kc.udpnetty.bean.MessageBean;
import kc.udpnetty.bean.ResendMessage;
import kc.udpnetty.netty.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import kc.udpnetty.callback.DiscardMessageCallback;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import kc.udpnetty.netty.UdpConfig;
import kc.udpnetty.netty.UdpManager;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class UdpClient implements Runnable {

    public static final String TAG = "UdpClient->";

    private ExecutorService executorService;

    private Channel mChannel;

    private final Lock mLock = new ReentrantLock();

    private final List<ResendMessage> resendMsgList = new ArrayList<>(1);
    private final DelayQueue<KeepSendMessage> keepMsgQueue = new DelayQueue<>();

    private DiscardMessageCallback mDiscardMessageCallback;

    @Override
    public void run() {
        ScheduledExecutorService reScheduledExecutorService = null;
        ScheduledExecutorService keepScheduledExecutorService = null;

        // 消息重发任务
        if (UdpManager.getInstance().getUdpConfig().isResendEnable()) {
            reScheduledExecutorService = new ScheduledThreadPoolExecutor(1, runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("scheduled-resend-" + thread.getId());
                return thread;
            });
            // 启用重发功能
            reScheduledExecutorService.scheduleAtFixedRate(this::needResend, 0L, UdpManager.getInstance().getUdpConfig().getResendInterval(), TimeUnit.MILLISECONDS);
        }

        // 消息连发任务
        if (UdpManager.getInstance().getUdpConfig().isKeepSendEnable()) {
            keepScheduledExecutorService = new ScheduledThreadPoolExecutor(1, runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("scheduled-keepSend-" + thread.getId());
                return thread;
            });
            // 启用连发功能
            keepScheduledExecutorService.execute(this::needKeepSend);
        }

        // Netty
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            mChannel = new Bootstrap()
                    .group(workGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))//解决netty udp接收、发送超过2048字节包
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new IdleStateHandler(0, 0,
                                            10, TimeUnit.SECONDS));
                            pipeline.addLast(UdpClientHandler.class.getSimpleName(), new UdpClientHandler(UdpClient.this));
                        }
                    })
                    .bind(0)
                    .sync()
                    .channel();
            log.info(TAG + "started");
            mChannel.closeFuture().await();
        } catch (InterruptedException ignore) {
            if (mChannel != null) {
                mChannel.close();
            }
        } finally {
            workGroup.shutdownGracefully();
            if (keepScheduledExecutorService != null) {
                keepScheduledExecutorService.shutdownNow();
            }
            if (reScheduledExecutorService != null) {
                reScheduledExecutorService.shutdownNow();
            }
            mLock.lock();
            try {
                resendMsgList.clear();
            } finally {
                mLock.unlock();
            }
            log.info(TAG + "closed");
        }
    }

    public void setDiscardMessageCallback(DiscardMessageCallback callback) {
        mDiscardMessageCallback = callback;
    }

    public void start() {
        execute(this);
    }

    public void stop() {
        shutdownNow();
    }

    /**
     * 发送心跳消息
     * @param heartbeatMsg      发送的消息
     */
    public void sendHeartbeatMessage(HeartbeatMessage heartbeatMsg) {
        sendMessage(heartbeatMsg);
    }

    /**
     * 发送重发消息
     *
     * @param resendMessage      发送的消息
     * @param isResend 是否重新发送
     */
    public void sendResendMessage(ResendMessage resendMessage, boolean isResend) {
        if (mChannel != null) {
            if (isResend) {
                mLock.lock();
                try {
                    // 向集合中添加重发消息
                    resendMsgList.add(resendMessage);
                } finally {
                    mLock.unlock();
                }
            } else {
                sendMessage(resendMessage);
            }
        }
    }
    private void removeResendMsg(int deviceID) {
        mLock.lock();
        try {
            resendMsgList.removeIf(bean -> bean.getDeviceID() == deviceID);
        } finally {
            mLock.unlock();
        }
    }

    /**
     * 发送连发消息
     *
     * @param keepMsg      发送的消息
     * @param isKeepSend 是否连续发送
     */
    public void sendKeepSendMessage(KeepSendMessage keepMsg, boolean isKeepSend) {
        if (mChannel != null) {
            if (isKeepSend) {
                mLock.lock();
                try {
                    // 向延迟队列添加连发消息
                    addKeepMsg(keepMsg);
                } finally {
                    mLock.unlock();
                }
            } else {
                sendMessage(keepMsg);
            }
        }
    }
    private void addKeepMsg(KeepSendMessage keepSendMessage) {
        UdpConfig udpConfig = UdpManager.getInstance().getUdpConfig();
        for (int i = 0; i < udpConfig.getKeepSendLimit(); i++) {
            keepMsgQueue.offer(new KeepSendMessage(keepSendMessage.getIp(), keepSendMessage.getPort(), keepSendMessage.getMsg(), udpConfig.getKeepSendInterval() * i));
        }
    }

    /**
     * 遍历列表，判断是否存在需要重发的信息
     */
    private void needResend() {
        UdpConfig udpConfig = UdpManager.getInstance().getUdpConfig();
        long resendInterval = udpConfig.getResendInterval();
        mLock.lock();
        try {
            Iterator<ResendMessage> iterator = resendMsgList.iterator();
            while (iterator.hasNext()) {
                ResendMessage bean = iterator.next();
                // 重新发送
                if ((System.currentTimeMillis() - bean.getSendTimeMillis()) >= resendInterval) {
                    sendMessage(bean);
                }
            }
        } finally {
            mLock.unlock();
        }
    }

    /**
     * 遍历延迟队列，判断是否存在需要连发的信息
     */
    @SuppressWarnings("InfiniteLoopStatement")
    private void needKeepSend() {
        while (true) {
            try {
                KeepSendMessage bean = keepMsgQueue.take();
                if (resendMsgList.size() == 0) {
                    // 连续发送
                    sendMessage(bean);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(MessageBean msg) {
        msg.setSendTimeMillis(System.currentTimeMillis());
        byte[] byteArray = MessageUtil.getHexBytes(msg.getMsg());
        mChannel.writeAndFlush(
                new DatagramPacket(
                        Unpooled.copiedBuffer(byteArray),
                        new InetSocketAddress(msg.getIp(), msg.getPort())
                )
        ).addListener(future -> {
            if (future != null) {
                log.info(TAG + "send to ip : " + msg.getIp() + ":" + msg.getPort() + ", isSuccess: " + future.isSuccess());
            }
        });
    }

    private void execute(Runnable runnable) {
        if (executorService == null || executorService.isShutdown()) {
            executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), runnable1 -> {
                Thread thread = new Thread(runnable1);
                thread.setName("udp-client-" + thread.getId());
                return thread;
            });
        }
        executorService.execute(runnable);
    }

    private void shutdownNow() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    public List<ResendMessage> getResendMsgList() {
        return resendMsgList;
    }

}
