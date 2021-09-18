package com.example.kafkastream.stream;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.time.Duration.ofMinutes;

@Component
public class MessageProcessingStream {

    @Autowired
    public MessageProcessingStream(StreamsBuilder streamsBuilder) {
        Serde<String> stringSerde = Serdes.String();
        System.out.println("Will start processing the info now: ");
        KStream<String, Long> quotes = streamsBuilder.stream("quotes", Consumed.with(stringSerde, stringSerde))
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
                .windowedBy(TimeWindows.of(ofMinutes(2)))
                .count()
                .toStream((k,v)-> k.key());

        quotes.print(Printed.toSysOut());

        quotes.to("counts", Produced.with(stringSerde, Serdes.Long()));
    }
}
