package kc.model.door;

import java.util.List;

public class Context {
    private OperationModel operationModel;

    public Context(OperationModel operationModel) {
        this.operationModel = operationModel;
    }

    public void operation(String deviceName, String action, List<String> list) {
        try {
            operationModel.check(deviceName)
                    .action(action,list)
                    .agvfeedBack();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
