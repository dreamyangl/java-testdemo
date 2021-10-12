package kc.udpnetty.netty;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 所有UdpManager的缓存
 * @Created CaoGang
 */
@Slf4j
public class ConnectCache {
    public static volatile Map<String, UdpManager> connectMap = new ConcurrentHashMap();
}
