package normal;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class NormalTest {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static ReentrantLock reentrantLock = new ReentrantLock();


    @Test(timeout = 1000)
    public void test() {
        while (true) {
            System.out.println(1);
        }
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() {
        System.out.println(1);
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            executorService.execute(NormalTest::sync);
        }

    }

    public static void sync() {
        boolean f = false;
        try {
            f = reentrantLock.tryLock(2, TimeUnit.SECONDS);
            if(f){
                System.out.println(f);
                Thread.sleep(3000);
                System.out.println(3);
            }
        } catch (Exception ex) {

        } finally {
            if (f) {
                reentrantLock.unlock();
            }
        }
    }
}
