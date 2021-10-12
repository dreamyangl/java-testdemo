package kc.netty.client.tcp;

import kc.struct.SocketInfosPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Created CaoGang
 * @Date 2021/7/8 11:51
 * @Version 1.0
 */
@Component
@Slf4j
public class TestNettyClient {
    ExecutorService pool = null;

    /*@Scheduled(fixedDelay = 1000L)
    public void printPoolCountActive(){
        Long threadCount = ((ThreadPoolExecutor)pool).getTaskCount();
        log.info("thread active count:[{}]",threadCount);
    }*/

    @PostConstruct
    public void startNettyClient(){
        List<SocketInfosPO> ledInfos = new ArrayList<>();
        ledInfos.add(new SocketInfosPO("172.31.218.244",10007,1));
        /*ledInfos.add(new LedInfos("172.31.252.47",8002));
        ledInfos.add(new LedInfos("172.31.252.47",8003));
        ledInfos.add(new LedInfos("172.31.252.47",8004));
        ledInfos.add(new LedInfos("172.31.252.47",8005));
        ledInfos.add(new LedInfos("172.31.252.47",8006));
        ledInfos.add(new LedInfos("172.31.252.47",8007));*/
        pool = Executors.newFixedThreadPool(ledInfos.size());
        for (SocketInfosPO ledInfo:ledInfos) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    start(ledInfo.getIp(), ledInfo.getPort());
                }
            });
        }
    }

    public void start(String ip,Integer port){
        NettyClient nettyClient = new NettyClient(ip,port);
        nettyClient.connect();
    }
}
