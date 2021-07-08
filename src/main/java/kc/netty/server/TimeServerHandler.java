package kc.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    public static volatile ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        byte[] b = new byte[bb.readableBytes()];
        bb.readBytes(b);
        System.out.println(Thread.currentThread().getName() + ":收到客户端数据：" + new String(b));
//        ByteBuf bf = Unpooled.copiedBuffer(("你好客户端$$1111111：" + Math.random()).getBytes());
//        ctx.writeAndFlush(bf);
        ((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String body = concurrentLinkedQueue.poll();
                    if (StringUtils.isEmpty(body)){
                        continue;
                    }
                    System.out.println(body);
                    ByteBuf bf = Unpooled.copiedBuffer(body.getBytes());
                    ctx.writeAndFlush(bf);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
