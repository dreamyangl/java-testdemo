package kc.netty.test.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MessageHandler extends ChannelInboundHandlerAdapter {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("关闭客户端连接");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.err.println("服务端链接不上，开始重连操作...");
                try {
                    NettyClient.connect(10007, "127.0.0.1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        while (true){
            executorService.execute(()->{
                System.out.println(concurrentLinkedQueue.size());
                String body = concurrentLinkedQueue.poll();
                if (StringUtils.isEmpty(body)){
                    return;
                }
                ByteBuf bf = Unpooled.copiedBuffer(body.getBytes());
                ctx.writeAndFlush(bf);
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        byte[] b = new byte[bb.readableBytes()];
        bb.readBytes(b);
        System.out.println("收到服务器端数据：" + new String(b));
    }

    public static void main(String[] args) {
        concurrentLinkedQueue.add("1");
    }
}
