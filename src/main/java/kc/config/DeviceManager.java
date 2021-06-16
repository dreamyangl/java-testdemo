package kc.config;

import com.google.common.collect.Maps;
import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2021/3/1 15:26
 */
@Component
public class DeviceManager {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final static Map<String, ModbusMaster> deviceMasters = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        try {
            String host = "127.0.0.1";
            int port = 502;
            TcpParameters tcpParameters = new TcpParameters();// 设置主机TCP参数
            tcpParameters.setHost(InetAddress.getByName(host));// TCP参数设置ip地址
            tcpParameters.setKeepAlive(true);// TCP设置长连接
            tcpParameters.setPort(port);// TCP设置端口,默认502
            // 创建一个主机
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_RELEASE);
            master.setResponseTimeout(1000);
            deviceMasters.put("d1", master);

            try {
                master.connect();
                log.info("device connect success. device:{}", host);
            } catch (ModbusIOException e) {
                log.error("device connect error. device:{}", host);
            }
        }catch (Exception ex){

        }

    }

    public static ModbusMaster getDeviceMaster(String name) {
        return deviceMasters.get(name);
    }


}
