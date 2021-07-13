package kc.netty.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description 无法连接、连接断开重连
 * @Created CaoGang
 * @Date 2021/7/8 15:43
 * @Version 1.0
 */
@Slf4j
public class ConnectionListener implements ChannelFutureListener {
    private NettyClient nettyClient;
    public ConnectionListener(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    log.info("服务端重连操作:[{}]",nettyClient.getIpAndPort());
                    try {
                        nettyClient.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("服务端重连异常:[{}]",nettyClient.getIpAndPort());
                    }
                }
            }, 10, TimeUnit.SECONDS);
        }
    }
}
