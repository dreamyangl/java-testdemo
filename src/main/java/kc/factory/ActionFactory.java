package kc.factory;

import kc.model.door.Close;
import kc.model.door.Open;
import kc.model.door.OperationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionFactory {
    @Autowired
    private Close close;
    @Autowired
    private Open open;

    public OperationModel getMode(String action) {
        switch (action) {
            case "OPEN":
                return open;
            case "CLOSE":
                return close;
            default:
                throw new RuntimeException("no this action:" + action);
        }
    }
}
