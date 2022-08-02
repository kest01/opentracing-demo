package ru.it1.tracing.servicea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.it1.tracing.common.SpanService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceAController {

    private final RestTemplate restTemplate;
    private final SpanService spanService;

    @GetMapping("/hello")
    public String hello(boolean raiseError) {
        spanService.addTagToSpan("raiseError", String.valueOf(raiseError));

        log.info("ServiceA controller call. TraceId = {}; raiseError={}", spanService.getCurrentTraceId(), raiseError);
        String result = restTemplate.getForObject("http://localhost:8081/hello?raiseError=" + raiseError, String.class);
        log.info("Result from ServiceB: {}", result);

        return result;
    }
}
