package kc.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@ChannelHandler.Sharable // @Sharable 标记这个类的实例可以在channel里共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty$$rockqwqwqw$$s!", CharsetUtil.UTF_8)); // 当被通知该 channel 是活动的时候就发送信息
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty$$rockqwqwqw$$s!", CharsetUtil.UTF_8)); // 当被通知该 channel 是活动的时候就发送信息
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd,HH:mm:ss:SSS");

        while (true) {
            date.setTime(System.currentTimeMillis());
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty$$rockqwqwqw$$s!", CharsetUtil.UTF_8));
            System.out.println("客户端发送数据:" + sdf.format(date));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8)); // 记录接收到的消息
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 记录日志错误并关闭 channel
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 发送数据到服务器端。
     *
     * @throws Exception
     */
    private void sendDataToServer(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd,HH:mm:ss:SSS");

        while (true) {
            date.setTime(System.currentTimeMillis());
            ctx.writeAndFlush("客户端@" + sdf.format(date));
            System.out.println("客户端发送数据:" + sdf.format(date));

            Thread.sleep(1000);
        }
    }
}
