package kc.udpnetty.bean;

/**
 * @author wangtong
 * @date 2021/9/6 18:23
 */
public class ResendMessage extends MessageBean{
    /**
     * 是否正确接收
     * <p>内部使用</p>
     */
    private boolean isReceived;

    public ResendMessage(String ip, int port, String msg) {
        super(ip, port, msg);
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }
}
