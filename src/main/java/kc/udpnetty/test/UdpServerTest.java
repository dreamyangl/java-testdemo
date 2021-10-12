package kc.udpnetty.test;

import lombok.extern.slf4j.Slf4j;
import kc.udpnetty.netty.UdpConfig;
import kc.udpnetty.netty.UdpManager;
import kc.udpnetty.callback.ReceiveMessageCallback;

import java.net.InetSocketAddress;

/**
 * @author wangtong
 * @date 2021/9/3 11:19
 */
@Slf4j
public class UdpServerTest {
    private static final UdpManager manager = UdpManager.getInstance();

    public static void main(String[] args) {
        UdpConfig config = new UdpConfig.Builder()
                .build();
        manager.init(config);
        manager.setReceiveMessageCallback(new ReceiveMessageCallback() {
            @Override
            public void onReceiveMsg(String msg, InetSocketAddress sender) {
                log.info("接收到的消息:" + msg);
            }

            @Override
            public void onException(Exception e) {
                System.out.println("异常:" + e.getMessage());
            }
        });

        manager.startServer(8088);
    }
}
