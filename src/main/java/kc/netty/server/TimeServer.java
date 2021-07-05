package kc.netty.server;
 
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
 
public class TimeServer {
	public void bind(int port) throws Exception{
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
 
		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	public static void main(String[] args) throws Exception {
		new TimeServer().bind(10007);
	}
}
class TimeServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bb = (ByteBuf) msg;
		byte[] b = new byte[bb.readableBytes()];
		bb.readBytes(b);
		System.out.println(Thread.currentThread().getName()+":收到客户端数据："+new String(b));
		ByteBuf bf = Unpooled.copiedBuffer(("你好客户端$$1111111："+Math.random()).getBytes());
		ctx.writeAndFlush(bf);
	}
}