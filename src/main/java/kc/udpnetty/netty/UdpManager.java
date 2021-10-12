package kc.udpnetty.netty;

import kc.udpnetty.bean.KeepSendMessage;
import kc.udpnetty.netty.client.UdpClient;
import kc.udpnetty.netty.server.UdpServer;
import kc.udpnetty.netty.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import kc.udpnetty.callback.DiscardMessageCallback;
import kc.udpnetty.callback.ReceiveMessageCallback;
import kc.udpnetty.common.MessageTypeEnum;

import java.net.InetSocketAddress;

@Slf4j
public class UdpManager {
    /**
     * 配置参数
     */
    private UdpConfig mUdpConfig;
    /**
     * 消息发送端
     */
    private UdpClient mUdpClient;

    /**
     * 消息接收端
     */
    private UdpServer mUdpServer;
    /**
     * 未发送成功消息回调
     */
    private DiscardMessageCallback mDiscardMessageCallback;
    /**
     * 接收消息回调
     */
    private ReceiveMessageCallback mReceiveMessageCallback;

    public UdpManager() {
    }

    private static final class SingleHolder {
        private static final UdpManager INSTANCE = new UdpManager();
    }

    public static UdpManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 设置丢失消息回调
     */
    public void setDiscardMessageCallback(DiscardMessageCallback mDiscardMessageCallback) {
        this.mDiscardMessageCallback = mDiscardMessageCallback;
    }

    /**
     * 设置接收消息回调，最好在{@link #startServer(int)} 之前调用，避免造成异常接收不到
     */
    public void setReceiveMessageCallback(ReceiveMessageCallback mReceiveMessageCallback) {
        this.mReceiveMessageCallback = mReceiveMessageCallback;
    }

    public void init(UdpConfig config) {
        mUdpConfig = config;
    }

    public UdpConfig getUdpConfig() {
        if (mUdpConfig == null) {
            //如果没有配置
            mUdpConfig = new UdpConfig.Builder().build();
        }
        return mUdpConfig;
    }

    /**
     * 只开启发送端
     */
    public void startClient() {
        if (mUdpClient == null) {
            //只用初始化一次即可
            mUdpClient = new UdpClient();
            mUdpClient.setDiscardMessageCallback(msg -> {
                if (mDiscardMessageCallback != null) {
                    mDiscardMessageCallback.onDiscardMsg(msg);
                }
            });
            mUdpClient.start();
        }
    }

    public void sendMessage(String ip, int port, String msg, MessageTypeEnum type) {
        if (!ip.matches(UdpConfigConstant.IP_REGEX)) {
            log.error("invalid ip address");
            return;
        }
        if (MessageUtil.isIllegalPort(port)) {
            log.error("invalid port");
            return;
        }
        if (msg == null || msg.isEmpty()) {
            log.error("send msg is null");
            return;
        }
        if (mUdpClient == null) {
            log.error("you need to start UdpClient");
            return;
        }
        switch (type.name()){
            // 握手消息
            case "RESENT":
                break;
            // 心跳消息
            case "HEARTBEAT":
                break;
            // 连发消息
            case "KEEP":
                mUdpClient.sendKeepSendMessage(new KeepSendMessage(ip, port, msg, mUdpConfig.getResendInterval()), false);
                break;
        }
    }


    /**
     * 只开启接收端
     *
     * @param inetPort 接收端监听的端口
     */
    public void startServer(int inetPort) {
        if (MessageUtil.isIllegalPort(inetPort)) {
            log.error("invalid port");
            return;
        }

        if (mUdpServer != null && inetPort == mUdpServer.getInetPort()) {
            //说明正在运行，并且监听的端口一致，无需重新启动
            return;
        }
        stopServer();
        mUdpServer = new UdpServer(inetPort, new ReceiveMessageCallback() {
            @Override
            public void onReceiveMsg(String msg, InetSocketAddress sender) {
                if (mReceiveMessageCallback != null) {
                    mReceiveMessageCallback.onReceiveMsg(msg, sender);
                }
            }

            @Override
            public void onException(Exception e) {
                if (mReceiveMessageCallback != null) {
                    mReceiveMessageCallback.onException(e);
                }
            }
        });
        mUdpServer.start();
    }

    /**
     * 关闭发送端
     */
    public void stopClient() {
        if (mUdpClient != null) {
            mUdpClient.stop();
            mUdpClient = null;
        }
    }

    /**
     * 关闭接收端
     */
    public void stopServer() {
        if (mUdpServer != null) {
            mUdpServer.stop();
            mUdpServer = null;
        }
    }

    /**
     * 开启发送端和接收端
     *
     * @param inetPort 接收端监听的端口
     */
    public void start(int inetPort) {
        startClient();
        startServer(inetPort);
    }

    /**
     * 关闭发送端和接收端
     */
    public void stop() {
        stopClient();
        stopServer();
    }

}
