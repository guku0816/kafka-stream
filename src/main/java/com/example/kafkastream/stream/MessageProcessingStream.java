package com.example.kafkastream.stream;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.time.Duration.ofMinutes;

@Component
/**
 *  Defines the topology that describes the processing of the messages and converting them to the desired format
 */
public class MessageProcessingStream {

    @Autowired
    public MessageProcessingStream(StreamsBuilder streamsBuilder,
                                   @Value(value = "${kafka.producer.topic.name}") String produceTo,
                                   @Value(value = "${kafka.consumer.topic.name}") String consumeFrom) {
        Serde<String> stringSerde = Serdes.String();
        System.out.println("Will start processing the info now: ");
        KStream<String, Long> quotes = streamsBuilder
                .stream(produceTo, Consumed.with(stringSerde, stringSerde)) // Read from the quotes topic.
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+"))) // split the value into a steam of words
                .groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde)) // group the values in the stream based on the value, the value becomes the key of the KTable
                .windowedBy(TimeWindows.of(ofMinutes(2))) // Process the messages into windows of 2 minutes. It resets after the window.
                .count() // Calculates the count of each key
                .toStream((k,v)-> k.key()); // the window operation converts the stream to KSteam<Windowed<String>, Long> we need to correct this and get a KStream<String, Long>

        quotes.print(Printed.toSysOut()); // Log all the messages being processed by the stream.

        quotes.to(consumeFrom, Produced.with(stringSerde, Serdes.Long())); // Move the Processed messages to the counts topic.
    }
}
