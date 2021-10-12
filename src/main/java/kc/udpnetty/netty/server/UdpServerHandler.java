package kc.udpnetty.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import udpnetty.netty.util.MessageUtil;

@Slf4j
class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String TAG = "UdpServerHandler->";

    private final UdpServer mUdpServer;

    public UdpServerHandler(UdpServer server) {
        mUdpServer = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
        if (msg != null) {
            log.info(TAG + "ip -> port:" + msg.sender().getHostString()+":"+msg.sender().getPort());
            ByteBuf buf = msg.copy().content();
            StringBuffer hexStr = new StringBuffer();
            for (int i=0;i< buf.readableBytes();i++){
                hexStr.append(MessageUtil.ByteToHexStr(buf.getByte(i)));
            }
            String message = hexStr.toString().toUpperCase();
            log.info(TAG + "channelRead0 -> msg:" + message);
            if (mUdpServer != null) {
                mUdpServer.receiveMessage(message, msg.sender());
            }
            byte[] byteArray = MessageUtil.getHexBytes("BB BB BB");
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(byteArray), msg.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx != null) {
            ctx.close();
        }
        log.error(TAG + "exceptionCaught:" + cause.getMessage());
    }
}
