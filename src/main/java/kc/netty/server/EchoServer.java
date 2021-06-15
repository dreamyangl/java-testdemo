package kc.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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

    public static void main(String[] args) throws Exception {
        int port = 4567;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start(); // 设计端口、启动服务器
    }
}
