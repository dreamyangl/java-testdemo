package kc.udpnetty.bean;

public class MessageBean {
    /**
     * 发送到目标的ip地址
     */
    private String ip;
    /**
     * 发送到目标的端口号
     */
    private int port;
    /**
     * 发送的消息
     */
    private String msg;

    /**
     * 消息发送的时间
     * <p>内部使用</p>
     */
    private long sendTimeMillis = System.currentTimeMillis();

    /**
     * 机器人信号接口设备ID
     */
    private int deviceID = 0;

    public MessageBean(String ip, int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getSendTimeMillis() {
        return sendTimeMillis;
    }

    public void setSendTimeMillis(long sendTimeMillis) {
        this.sendTimeMillis = sendTimeMillis;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", msg='" + msg + '\'' +
                ", deviceID=" + deviceID +
                '}';
    }
}
