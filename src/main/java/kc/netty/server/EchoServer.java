package kc.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(); // 创建 EventLoopGroup

        try {
            ServerBootstrap bootstrap = new ServerBootstrap(); // 创建 ServerBootstrap
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class) // 指定使用 NIO 的传输 Channel
                    .localAddress(new InetSocketAddress(port)) // 设置 socket 地址使用所选的端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //首先创建分隔符缓冲对象ByteBuf，本例程中使用“$_”作为分隔符。
                            ByteBuf delimiter = Unpooled.copiedBuffer("$$".getBytes());
                            //创建DelimiterBasedFrameDecoder对象，将其加入到ChannelPipeline中。
                            //DelimiterBasedFrameDecoder有多个构造方法，这里我们传递两个参数，
                            //第一个1024表示单条消息的最大长度，当达到该长度后仍然没有查找到分隔符，
                            //就抛出TooLongFrame Exception异常，防止由于异常码流缺失分隔符导致的内存溢出，
                            //这是Netty解码器的可靠性保护；第二个参数就是分隔符缓冲对象。
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(100,delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync(); // 绑定的服务器;sync 等待服务器关闭
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync(); // 关闭 channel 和 块，直到它被关闭
        } finally {
            group.shutdownGracefully().sync(); // 关闭 EventLoopGroup，释放所有资源。
        }
    }

     class ChildChannelHandler extends ChannelInitializer {
        @Override
        protected void initChannel(Channel arg0) throws Exception {
            //首先创建分隔符缓冲对象ByteBuf，本例程中使用“$_”作为分隔符。
            ByteBuf delimiter = Unpooled.copiedBuffer("$$".getBytes());
            //创建DelimiterBasedFrameDecoder对象，将其加入到ChannelPipeline中。
            //DelimiterBasedFrameDecoder有多个构造方法，这里我们传递两个参数，
            //第一个1024表示单条消息的最大长度，当达到该长度后仍然没有查找到分隔符，
            //就抛出TooLongFrame Exception异常，防止由于异常码流缺失分隔符导致的内存溢出，
            //这是Netty解码器的可靠性保护；第二个参数就是分隔符缓冲对象。
            arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new EchoServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 4567;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start(); // 设计端口、启动服务器
    }
}
