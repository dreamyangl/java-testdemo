package kc.udpnetty.test;

import lombok.extern.slf4j.Slf4j;
import kc.udpnetty.netty.UdpConfig;
import kc.udpnetty.netty.UdpManager;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UdpClientTest {

    public static void main(String[] args) {
        UdpManager manager = UdpManager.getInstance();
        UdpConfig config = new UdpConfig.Builder()
                .setKeepSendLimit(3)
                .setKeepSendEnable(true)
                .setKeepSendInterval(200L)
                .build();
        manager.init(config);
        manager.setDiscardMessageCallback(msg -> System.out.println("丢弃消息ip :" + msg.getIp() + ",port:" + msg.getPort() + ",msg:" + msg.getMsg()));

        manager.startClient();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}