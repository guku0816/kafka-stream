package com.example.kafkastream.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.producer.topic.name}")
    private String produceTo;

    @Value(value = "${kafka.consumer.topic.name}")
    private String consumeFrom;
    @Bean
    NewTopic quotes(){
        return new NewTopic(produceTo, 3, (short)1);
    }

    @Bean
    NewTopic counts(){
        return new NewTopic(consumeFrom, 3, (short)1);
    }
}
