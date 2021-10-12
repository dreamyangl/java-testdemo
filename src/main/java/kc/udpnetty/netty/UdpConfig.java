package kc.udpnetty.netty;


public class UdpConfig {
    /**
     * 是否启用重发
     */
    private final boolean resendEnable;
    /**
     * 消息重发之间的间隔时间,单位：毫秒
     */
    private final long resendInterval;

    /**
     * 是否启用连发
     */
    private final boolean keepSendEnable;
    /**
     * 消息连发次数
     */
    private final int keepSendLimit;
    /**
     * 消息连发之间的间隔时间,单位：毫秒
     */
    private final long keepSendInterval;

    private UdpConfig(Builder builder) {
        this.resendEnable = builder.resendEnable;
        this.resendInterval = builder.resendInterval;

        this.keepSendEnable = builder.keepSendEnable;
        this.keepSendLimit = builder.keepSendLimit;
        this.keepSendInterval = builder.keepSendInterval;
    }
    public boolean isResendEnable() {
        return resendEnable;
    }

    public long getResendInterval() {
        return resendInterval;
    }

    public boolean isKeepSendEnable() {
        return keepSendEnable;
    }

    public int getKeepSendLimit() {
        return keepSendLimit;
    }

    public long getKeepSendInterval() {
        return keepSendInterval;
    }

    public static final class Builder {
        /**
         * 是否启用重发
         */
        private boolean resendEnable = UdpConfigConstant.DEFAULT_RESEND_ENABLE;
        /**
         * 重发之间的间隔时间,单位：毫秒
         */
        private long resendInterval = UdpConfigConstant.DEFAULT_RESEND_INTERVAL;

        /**
         * 是否启用连发
         */
        private boolean keepSendEnable = UdpConfigConstant.DEFAULT_KEEPSEND_ENABLE;
        /**
         * 连发次数
         */
        private int keepSendLimit = UdpConfigConstant.DEFAULT_KEEPSEND_LIMIT;
        /**
         * 连发之间的间隔时间,单位：毫秒
         */
        private long keepSendInterval = UdpConfigConstant.DEFAULT_KEEPSEND_INTERVAL;

        public Builder setResendEnable(boolean resendEnable) {
            this.resendEnable = resendEnable;
            return this;
        }

        public Builder setResendInterval(long resendInterval) {
            this.resendInterval = resendInterval;
            return this;
        }

        public Builder setKeepSendEnable(boolean keepSendEnable) {
            this.keepSendEnable = keepSendEnable;
            return this;
        }

        public Builder setKeepSendLimit(int keepSendLimit) {
            if (keepSendLimit <= 0) {
                keepSendLimit = UdpConfigConstant.DEFAULT_KEEPSEND_LIMIT;
            }
            this.keepSendLimit = keepSendLimit;
            return this;
        }

        public Builder setKeepSendInterval(long keepSendInterval) {
            this.keepSendInterval = keepSendInterval;
            return this;
        }

        public UdpConfig build() {
            return new UdpConfig(this);
        }
    }
}
