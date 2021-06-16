package kc.testClasses;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.sun.media.sound.SoftTuning;
import kc.TestStarter;
import kc.config.DeviceManager;
import org.junit.Test;

public class TestModBus extends TestStarter {
    @Test
    public void test1() throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        ModbusMaster modbusMaster = DeviceManager.getDeviceMaster("d1");
        boolean[] f = modbusMaster.readCoils(1,0,8);
        for (int i = 0; i < f.length; i++) {
            System.out.println(f[i]);
        }
    }
}
