package kc.udpnetty.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import udpnetty.bean.HeartbeatMessage;
import udpnetty.bean.ResendMessage;
import kc.udpnetty.common.MessageTypeEnum;
import kc.udpnetty.netty.ConnectCache;
import kc.udpnetty.netty.UdpManager;
import udpnetty.netty.util.DataUtil;
import udpnetty.netty.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> implements Runnable{
    public static final String TAG = "UdpClientHandler->";
    private String ip;
    private Integer port;
    private final List<HeartbeatMessage> heartbeatMsgList = new ArrayList<>(2);

    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("scheduled-heartbeatSend-" + thread.getId());
        return thread;
    });

    private final UdpClient mUdpClient;

    public UdpClientHandler(UdpClient client) {
        mUdpClient = client;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
        ip = msg.sender().getHostString();
        port = msg.sender().getPort();

        List<ResendMessage> resendMsgList = mUdpClient.getResendMsgList();

        log.info(TAG + "ip -> port:" + ip +":"+ port);
        ByteBuf buf = msg.copy().content();
        StringBuffer hexStr = new StringBuffer();
        for (int i=0;i< buf.readableBytes();i++){
            hexStr.append(MessageUtil.ByteToHexStr(buf.getByte(i)));
        }
        String message = hexStr.toString().toUpperCase();
        log.info(TAG + "channelRead0 -> msg:" + message);
        // 如果接收到对应握手指令的回复,清空 resendMsgList
        if (DataUtil.isHandshakeReply(resendMsgList.get(0).getMsg(), message)) {
            resendMsgList.clear();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx != null) {
            ctx.close();
        }
        log.error(TAG + "exceptionCaught:" + cause.getMessage());
    }

    /**
     * 心跳包发送
     */
    @Override
    public void run() {
        // 清除心跳成功的数据包
        clearHeartbeatSuccess();
        // 如果还有未回复的心跳包,并且发送时间超过3秒,需要重新握手
        if (heartbeatMsgList.size() > 0) {
            for (HeartbeatMessage heartbeatMessage : heartbeatMsgList) {
                if (heartbeatMessage.getSendTimeMillis() > 3) {

                }
            }
        }
        String heartbeatMsg = DataUtil.getHeartbeatMsg();
        // 定时发送心跳数据
        if (!StringUtils.isEmpty(ip) && !StringUtils.isEmpty(String.valueOf(port))) {
            UdpManager udpManager = ConnectCache.connectMap.get(ip);
            udpManager.sendMessage(ip, port, heartbeatMsg, MessageTypeEnum.HEARTBEAT);
        }
    }

    private void clearHeartbeatSuccess() {

    }

    public void start() {
        execute(this);
    }

    private void execute(Runnable runnable) {
        executorService.scheduleAtFixedRate(runnable, 0, 3L, TimeUnit.SECONDS);
    }

    private void shutdownNow() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

}
