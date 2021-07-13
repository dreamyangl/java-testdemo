package kc.controller;

import kc.netty.clientold.MessageHandler;
import kc.netty.server.TimeServerHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public Integer test(@RequestBody int num){
        return num;
    }

    @PostMapping("/send-client-message")
    public Integer sendClientMessage(@RequestBody String body){
        TimeServerHandler.concurrentLinkedQueue.offer(body);
        return 0;
    }

    @PostMapping("/send-server-message")
    public Integer sendServerMessage(@RequestBody String body){
        MessageHandler.concurrentLinkedQueue.offer(body);
        return 0;
    }
}
