package kc;

//import kc.netty.client.MessageHandler;
import kc.netty.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
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
        NettyClient.connect(10007,"127.0.0.1");
//        while (true){
//            MessageHandler.concurrentLinkedQueue.add("11111$$2222$$");
//            Thread.sleep(1000);
//        }
    }
}