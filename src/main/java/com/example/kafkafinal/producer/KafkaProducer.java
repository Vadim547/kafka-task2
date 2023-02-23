package com.example.kafkafinal.producer;

import com.example.kafkafinal.dto.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Vehicle> kafkaTemplate;

    public void send(String topic, Vehicle data) {
        LOGGER.info("sending payload='{}' to topic='{}'", data, topic);
        Message<Vehicle> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, data.getId()).build();

        kafkaTemplate.send(message);
    }
}
