package kc.udpnetty.netty.util;

/**
 * @author wangtong
 * @date 2021/9/9 12:31
 */
public class DataUtil {
    public static boolean isHandshakeReply(String resendMessage, String message) {
        return true;
    }


    public static String getHandshakeMsg() {
        return "55 11 00 00 00 0E E9 60 DF 9B C4 FD B4 1C 38 69 88 04 DD";
    }

    public static String getHeartbeatMsg() {
        return "55 10 00 00 00 0D 00 00 00 00 00 00 00 00 00 AA 55 DD";
    }
}
