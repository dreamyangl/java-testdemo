package kc.model.led;

import org.springframework.stereotype.Service;

@Service
public class PutOutAction extends LedModel{
    @Override
    public void action() {
        System.out.println("灭灯操作");
    }
}
