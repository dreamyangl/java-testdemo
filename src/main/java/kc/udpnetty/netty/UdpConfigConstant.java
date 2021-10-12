package kc.udpnetty.netty;

class UdpConfigConstant {
    /**
     * 是否启用重发机制
     */
    static final boolean DEFAULT_RESEND_ENABLE = true;
    /**
     * 三秒重发一次
     */
    static final long DEFAULT_RESEND_INTERVAL = 3000L;

    /**
     * 是否启用连发机制
     */
    static final boolean DEFAULT_KEEPSEND_ENABLE = true;
    /**
     * 连发次数限制
     */
    static final int DEFAULT_KEEPSEND_LIMIT = 3;
    /**
     * 两秒连发一次
     */
    static final long DEFAULT_KEEPSEND_INTERVAL = 2000L;

    static final String IP_REGEX = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
}
