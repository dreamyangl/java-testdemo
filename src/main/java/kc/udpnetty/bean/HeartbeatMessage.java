package kc.udpnetty.bean;

/**
 * @author wangtong
 * @date 2021/9/9 18:05
 */
public class HeartbeatMessage extends MessageBean{

    public HeartbeatMessage(String ip, int port, String msg) {
        super(ip, port, msg);
    }
}
