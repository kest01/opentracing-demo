package ru.it1.tracing.serviceb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServiceBController {

    @GetMapping("/hello")
    public String hello() {
        log.info("ServiceB controller call");
        return "Hello world from Service B!";
    }

}
