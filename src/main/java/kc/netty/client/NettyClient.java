package kc.netty.client;
 
 
import java.util.concurrent.TimeUnit;
 
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class NettyClient {
	public static void connect(int port,String host) throws Exception{
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {
 
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$$".getBytes());
					//创建DelimiterBasedFrameDecoder对象，将其加入到ChannelPipeline中。
					//DelimiterBasedFrameDecoder有多个构造方法，这里我们传递两个参数，
					//第一个1024表示单条消息的最大长度，当达到该长度后仍然没有查找到分隔符，
					//就抛出TooLongFrame Exception异常，防止由于异常码流缺失分隔符导致的内存溢出，
					//这是Netty解码器的可靠性保护；第二个参数就是分隔符缓冲对象。
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(100,delimiter));
					ch.pipeline().addLast(new MessageHandler());
				}
			});
			ChannelFuture f = b.connect(host, port);
			f.addListener(new ConnectionListener());
			f.channel().closeFuture().sync();
		} finally {
			//group.shutdownGracefully();
		}
	}
	public static void main(String[] args){
		try {
			connect(10007, "127.0.0.1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class MessageHandler extends ChannelInboundHandlerAdapter{
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
	}
}
class ConnectionListener implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
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
        } else {
            System.err.println("服务端链接成功...");
        }
    }
}