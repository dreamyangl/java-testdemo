package kc.netty.clientold;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kc.service.DoSomeThing;
import kc.util.SpringBeanFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class MessageHandler extends ChannelInboundHandlerAdapter {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringBeanFactory.getBean("taskExecutorService", ThreadPoolTaskExecutor.class);
    private DoSomeThing doSomeThing = SpringBeanFactory.getBean(DoSomeThing.class);
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
//        while (true) {
//            String body = concurrentLinkedQueue.poll();
//            threadPoolTaskExecutor.execute(() -> {
//                System.out.println(Thread.currentThread().getName() + ":" + body);
////					String body = "你好服务端：+Math.random())";
//                if (StringUtils.isEmpty(body)) {
//                    return;
//                }
//                ByteBuf bf = Unpooled.copiedBuffer(body.getBytes());
//                ctx.writeAndFlush(bf);
//            });
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        new Thread(() -> {
            while (true) {
                String body = concurrentLinkedQueue.poll();
//					System.out.println(Thread.currentThread().getName()+":"+body);
//                String body = "你好服务端：+Math.random())";
                if (StringUtils.isEmpty(body)) {
                    continue;
                }
                ByteBuf bf = Unpooled.copiedBuffer(body.getBytes());
                ctx.writeAndFlush(bf);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        byte[] b = new byte[bb.readableBytes()];
        bb.readBytes(b);
        System.out.println(Thread.currentThread().getName() + ":收到服务器端数据：" + new String(b));
//        doSomeThing.test(new String(b));
    }
}
