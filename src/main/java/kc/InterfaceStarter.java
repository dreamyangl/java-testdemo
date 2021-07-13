package kc;

import kc.netty.clientold.NettyClient;
import kc.netty.server.TimeServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterfaceStarter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    MessageHandler.concurrentLinkedQueue.offer("你好服务端："+Math.random());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeServer.bind(10007);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
        NettyClient.connect(10007,"127.0.0.1");
    }
}