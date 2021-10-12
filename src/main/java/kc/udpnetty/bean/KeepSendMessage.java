package kc.udpnetty.bean;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wangtong
 * @date 2021/9/6 17:57
 */
public class KeepSendMessage extends MessageBean implements Delayed {

    /**
     * 延迟时间
     */

    private long delay;
    /**
     * 到期时间
     */
    private long expire;

    public KeepSendMessage(String ip, int port, String msg, long delay) {
        super(ip, port, msg);
        this.delay = delay;
        //到期时间 = 当前时间+延迟时间
        expire = System.currentTimeMillis() + delay;
    }

    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
