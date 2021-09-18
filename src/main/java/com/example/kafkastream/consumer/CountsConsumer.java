package com.example.kafkastream.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountsConsumer {

    @KafkaListener(topics = "${kafka.consumer.topic.name}", groupId = "${kafka.consumer.group.id}")
    public void consumeMessage(ConsumerRecord<String, Long> record){
        log.info("Key {} count {}", record.key(), record.value());
        Long count = record.value();
        if(count > 5){
            log.info("We need to Execute logic here");
        } else {
            log.info("Within permissible limits");
        }
    }
}
