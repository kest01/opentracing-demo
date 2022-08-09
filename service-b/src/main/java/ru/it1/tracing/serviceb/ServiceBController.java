package ru.it1.tracing.serviceb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.it1.tracing.common.SpanService;

import java.util.Map;

import static ru.it1.tracing.common.TestUtils.sleep;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceBController {

    private final SpanService spanService;
    private final KafkaSender kafkaSender;

    @GetMapping("/hello")
    public String hello(boolean raiseError) {
        spanService.addTagToSpan("raiseError", String.valueOf(raiseError));
        log.info("ServiceB controller call. TraceId = {}; raiseError={}", spanService.getCurrentTraceId(), raiseError);

        kafkaSender.send(raiseError);

        return spanService.runInNewSpan(
                "Internal call",
                () -> {
                    log.info("Message from internal call");
                    sleep(20);
                    return "Hello world from Service B!";
                },
                Map.of("internal tag", "internal tag value")
        );
    }

}
