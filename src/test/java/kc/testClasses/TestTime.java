package kc.testClasses;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestTime {
    @Test
    public void test() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        System.out.println(System.currentTimeMillis());
        int second = 3;
        scheduledExecutorService.schedule(() -> {
            //解锁成功后移除这个任务
            System.out.println("done");
        }, 3, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        long start = System.currentTimeMillis()/1000 +60;
        int second = 3;
//        Thread.sleep(3);
        System.out.println(start - System.currentTimeMillis()/1000);
//        scheduledExecutorService.schedule(() -> {
//            //解锁成功后移除这个任务
//            System.out.println("done");
//        }, 3, TimeUnit.SECONDS);
    }
}
