package kc.udpnetty.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageUtil {

    public static int byteArrayToInt(byte[] byteArray) {
        if (byteArray.length != 4) {
            return 0;
        }
        return byteArray[3] & 0xFF |
                (byteArray[2] & 0xFF) << 8 |
                (byteArray[1] & 0xFF) << 16 |
                (byteArray[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[4 - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    /**
     * 分割包数据
     */
    public static void splitDatagramPacket(byte[] bytes, byte[] lengthArray, byte[] idArray, byte[] numArray) {
        System.arraycopy(bytes, 0, lengthArray, 0, lengthArray.length);
        System.arraycopy(bytes, 4, idArray, 0, idArray.length);
        System.arraycopy(bytes, 8, numArray, 0, numArray.length);
    }

    /**
     * 是否是非法的端口
     *
     * @param port
     * @return
     */
    public static boolean isIllegalPort(int port) {
        return port < 0 || port > 65535;
    }

    /**
     * 将16进制字符串消息转换为netty的buff消息
     * @param msg
     * @return
     */
    public static ByteBuf bufMessage(String msg){
        //netty需要用ByteBuf传输
        ByteBuf buff = Unpooled.buffer();
        //对接需要16进制
        buff.writeBytes(getHexBytes(msg));
        return buff;
    }
    /**
     * 普通byte[]转16进制Str
     * @param str
     */
    public static String ByteToHexStr(byte str) {
        StringBuilder stringBuilder = new StringBuilder();
        String hex = Integer.toHexString(str & 0xFF).toUpperCase();
        if (hex.length() == 1) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hex);
        return stringBuilder.toString();
    }

    /**
     * 将16进制的字符串转成字符数组
     * @param str
     * @return
     */
    public static byte[] getHexBytes(String str) {
        str = str.replaceAll(" ", "");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
}
