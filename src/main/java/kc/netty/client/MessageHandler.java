package kc.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kc.service.DoSomeThing;
import kc.util.SpringBeanFactory;

import java.util.concurrent.TimeUnit;

class MessageHandler extends ChannelInboundHandlerAdapter {
	private DoSomeThing doSomeThing = SpringBeanFactory.getBean(DoSomeThing.class);
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					ByteBuf bf = Unpooled.copiedBuffer(("你好服务端："+Math.random()).getBytes());
					ctx.writeAndFlush(bf);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bb = (ByteBuf) msg;
		byte[] b = new byte[bb.readableBytes()];
		bb.readBytes(b);
		System.out.println("收到服务器端数据："+new String(b));
		doSomeThing.test(new String(b));
	}
}
