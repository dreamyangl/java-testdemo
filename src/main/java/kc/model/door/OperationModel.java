package kc.model.door;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 自动门模型
 */
@Component
public abstract class OperationModel {
    public List<String> agvs;

    /**
     * 放在这里会执行两次init,不妥
     */
    public void init() {
        System.out.println("execute init");
    }

    /**
     * 校验
     *
     * @param deviceName
     * @return
     */
    OperationModel check(String deviceName) {
        if (StringUtils.isEmpty(deviceName)) {
            throw new RuntimeException("deviceName is null");
        }
        System.out.println("execute check");
        return this;
    }

    /**
     * 自动门执行操作
     */
    abstract OperationModel action(String action,List<String> list);

    /**
     * 通知agv
     */
    OperationModel agvfeedBack() {
        System.out.println("execute agvfeedBack");
        return this;
    }

}
