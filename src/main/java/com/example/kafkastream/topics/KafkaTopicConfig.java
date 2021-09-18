package com.example.kafkastream.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic quotes(){
        return new NewTopic("quotes", 3, (short)1);
    }

    @Bean
    NewTopic counts(){
        return new NewTopic("counts", 3, (short)1);
    }
}
