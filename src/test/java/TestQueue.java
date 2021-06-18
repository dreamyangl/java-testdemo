import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 非阻塞队列：ConcurrentLinkedQueue(无界线程安全)，采用CAS机制（compareAndSwapObject原子操作）。
 * 阻塞队列：ArrayBlockingQueue(有界)、LinkedBlockingQueue（无界）、DelayQueue、PriorityBlockingQueue，采用锁机制；使用 ReentrantLock 锁。
 *关于ConcurrentLinkedQueue和LinkedBlockingQueue：
 *
 *     1.LinkedBlockingQueue是使用锁机制，ConcurrentLinkedQueue是使用CAS算法，虽然LinkedBlockingQueue的底层获取锁也是使用的CAS算法
 *
 *     2.关于取元素，ConcurrentLinkedQueue不支持阻塞去取元素，LinkedBlockingQueue支持阻塞的take()方法，如若大家需要ConcurrentLinkedQueue的消费者产生阻塞效果，需要自行实现
 *
 *     3.关于插入元素的性能，从字面上和代码简单的分析来看ConcurrentLinkedQueue肯定是最快的，但是这个也要看具体的测试场景，我做了两个简单的demo做测试，测试的结果如下，两个的性能差不多，但在实际的使用过程中，尤其在多cpu的服务器上，有锁和无锁的差距便体现出来了，ConcurrentLinkedQueue会比LinkedBlockingQueue快很多：
 */
public class TestQueue {
    @Test
    public void test1() {
        //线程不安全
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        //cas实现
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.add(1);
        //使用ReentrantLock
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        linkedBlockingQueue.add(1);
    }
}
