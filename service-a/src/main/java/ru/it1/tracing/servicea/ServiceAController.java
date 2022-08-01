package ru.it1.tracing.servicea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceAController {

    private final RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        log.info("ServiceA controller call");
        String result = restTemplate.getForObject("http://localhost:8081/hello", String.class);
        log.info("Result from ServiceB: {}", result);
        return result;
    }
}
