package kc.netty.client;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 所有连接的缓存
 * @Created CaoGang
 * @Date 2021/7/8 17:59
 * @Version 1.0
 */
@Slf4j
public class ConnectCache {
    public static volatile Map<String, ChannelHandlerContext> connectMap = new ConcurrentHashMap();
    /**
     * @param msg      需要发送的消息内容
     * @param ip_port  连接通道唯一id,连接的IP_端口
     * @author 曹刚
     * @Date 2021/07/08 16:10
     * @return: void
     */
    public static void sendMessage(String ip_port, String msg) {
        if (connectMap == null){
            log.info("连接缓存Map为空");
            return;
        }
        ChannelHandlerContext ctx = connectMap.get(ip_port);
        if (ctx == null && !ctx.channel().isActive()){
            log.info("通道不存在,或者已关闭:[{}]",ip_port);
            return;
        }
        //将客户端的信息直接返回写入ctx
        synchronized (ctx){
            ctx.writeAndFlush(LocalDateTime.now() + ":" +msg );
        }
    }
}
