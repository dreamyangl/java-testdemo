package kc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description 客户端
 * @Created CaoGang
 * @Date 2021/7/8 11:29
 * @Version 1.0
 */
@Slf4j
@Data
public class NettyClient{
    private String  IP;
    private Integer PORT;
    private Channel channel;
    Bootstrap bootstrap;

    public String getIpAndPort(){
        return getIP()+"_"+getPORT();
    }
    public NettyClient(){

    }
    public NettyClient(String ip,Integer port) {
        this.IP = ip;
        this.PORT = port;
    }

    /**
     * 创建连接
     */
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        //设置工作线程
        bootstrap.group(group)
                //初始化channel
                .channel(NioSocketChannel.class)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true);
                //设置handler管道
                bootstrap.handler(
                        new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel channel){
                                //channel.pipeline().addLast(new StringDecoder());
                                channel.pipeline().addLast(new StringEncoder());
                                // 指定时间内未收到服务端的数据,就向服务端发一个数据包
                                channel.pipeline().addLast(new IdleStateHandler(
                                        0, 10, 0, TimeUnit.SECONDS));
                                // 消息接收的监听类
                                channel.pipeline().addLast(new NettyClientHandler(NettyClient.this));
                            }
                });
        // 开始连接服务端
        try {
            ChannelFuture future = bootstrap.connect(IP, PORT);
            future.addListener(new ConnectionListener(NettyClient.this));
            channel = future.channel();
        }catch (Exception e){
            log.info("netty connect error [{}]",e.getMessage());
        }
    }
}
