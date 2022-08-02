package ru.it1.tracing.servicea.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "test-topic")
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("Message received: {}", record);

        if (record.value().equals("true")) {
            throw new RuntimeException("Oops!");
        }
    }

}
