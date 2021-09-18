package com.example.kafkastream.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Value(value = "${kafka.consumer.group.id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Long> consumerFactory(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String , Long> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;
    }


}
