package normal;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TestQueue {
    public static volatile ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    concurrentLinkedQueue.offer("111");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("结束");
            }
        }).start();
        System.out.println(concurrentLinkedQueue.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true){
                    String s = concurrentLinkedQueue.poll();
                    i = i+1;
                    System.out.println(s);
                    if (StringUtils.isEmpty(s)){
                        System.out.println(i);
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
