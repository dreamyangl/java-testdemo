package kc.udpnetty.test;

import lombok.extern.slf4j.Slf4j;
import kc.udpnetty.netty.UdpConfig;
import kc.udpnetty.netty.UdpManager;
import kc.udpnetty.callback.ReceiveMessageCallback;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UdpTest {

    public static void main(String[] args) {
        UdpManager manager = UdpManager.getInstance();
        UdpConfig config = new UdpConfig.Builder()
                .setKeepSendLimit(3)
                .setKeepSendEnable(true)
                .setKeepSendInterval(200L)
                .build();
        manager.init(config);
        manager.setDiscardMessageCallback(msg -> System.out.println("丢弃消息ip :" + msg.getIp() + ",port:" + msg.getPort() + ",msg:" + msg.getMsg()));
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

        manager.start(8088);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}