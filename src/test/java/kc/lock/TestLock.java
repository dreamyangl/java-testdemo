package kc.lock;

import junit.runner.BaseTestRunner;
import kc.TestStarter;
import kc.service.DoSomeThing;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.util.concurrent.locks.Lock;

public class TestLock extends TestStarter {
    @Autowired
    private RedisLockRegistry redisLockRegistry;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DoSomeThing doSomeThing;

    // 锁测试
    @Test
    public void test() {
        String lockKey = "test";
        // 获取锁
        Lock lock = redisLockRegistry.obtain(lockKey);
        // 加锁
        lock.lock();
        try {
            // 此处是你的代码逻辑，处理需要加锁的一些事务
            System.out.println(3333);
        } catch (Exception e) {
        } finally {
            // 配合解锁逻辑
            lock.unlock();
        }

    }

    @Test
    public void test1() {
        doSomeThing.putCache("s1").forEach(System.out::println);
    }
}
