package kc.netty.client.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @Description 客户端消息处理类
 * @Created CaoGang
 * @Date 2021/7/8 11:32
 * @Version 1.0
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private NettyClient nettyClient;

    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        /*log.info("心跳检测开始:[{}]",nettyClient.getIpAndPort());
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.WRITER_IDLE){
                ctx.writeAndFlush("I haven't heard from you for a long time");
            }
        }*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ConnectCache.connectMap.put(nettyClient.getIpAndPort(),ctx);
        log.info("服务端连接成功:[{}]",nettyClient.getIpAndPort());
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有服务端端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("服务端断了连接:[{}]",nettyClient.getIpAndPort());
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                //log.info("开始掉线重连接:[{}]",nettyClient.getIpAndPort());
                try {
                    nettyClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, CommonParam.HEARTBEAT_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            String value = ((ByteBuf) msg).toString(Charset.defaultCharset());
            log.info("接收服务端的数据:[{}],[{}]",nettyClient.getIpAndPort(),value);
            log.info("接收服务端的数据:[{}],[{}]",nettyClient.getIpAndPort(),value);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("服务端发生异常:[{}]",cause.getMessage());
    }
}
