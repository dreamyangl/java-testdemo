package kc.service;

import org.springframework.stereotype.Component;

@Component
public class DoSomeThing {

    public void test(String body) {
        System.out.println("处理消息" + body);
    }
}
