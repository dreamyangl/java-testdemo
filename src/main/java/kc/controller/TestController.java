package kc.controller;

import kc.netty.server.TimeServerHandler;
import kc.struct.NumDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public Integer test(@RequestBody int num){
        return num;
    }

    @PostMapping("/send-message")
    public Integer test(@RequestBody String body){
        TimeServerHandler.concurrentLinkedQueue.offer(body);
        return 0;
    }
}
