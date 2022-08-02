package ru.it1.tracing.serviceb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @NewSpan("Nested span")
    public void send(boolean raiseError) {
        log.info("Sending message to kafka");
        kafkaTemplate.send("test-topic", "message key", String.valueOf(raiseError))
                .addCallback(
                        result -> log.info("message successfully sent: {}", result),
                        ex -> log.error("Error sending message to kafka", ex)
                );
    }

}
