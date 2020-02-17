package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    String welmessage;
    public WelcomeController(@Value("${welcome.message}") String msg)
    {
        welmessage = msg;
    }
    @GetMapping("/")
    public String sayHello1() {
        return welmessage;
    }

}