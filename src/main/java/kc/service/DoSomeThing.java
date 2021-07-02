package kc.service;

import org.springframework.stereotype.Component;

@Component
public class DoSomeThing {

    public void test(String body){
        System.out.println(body);
    }
}
