package kc.netty.clientold;
 
 
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class NettyClient {
	public static void connect(int port,String host) throws Exception{
		NioEventLoopGroup group = new NioEventLoopGroup(10);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$".getBytes());
					//创建DelimiterBasedFrameDecoder对象，将其加入到ChannelPipeline中。
					//DelimiterBasedFrameDecoder有多个构造方法，这里我们传递两个参数，
					//第一个1024表示单条消息的最大长度，当达到该长度后仍然没有查找到分隔符，
					//就抛出TooLongFrame Exception异常，防止由于异常码流缺失分隔符导致的内存溢出，
					//这是Netty解码器的可靠性保护；第二个参数就是分隔符缓冲对象。
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
//					ch.pipeline().addLast(new StringDecoder());
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
