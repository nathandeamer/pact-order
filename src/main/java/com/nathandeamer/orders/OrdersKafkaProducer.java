package com.nathandeamer.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersKafkaProducer {

    private final ObjectMapper mapper = new ObjectMapper();

    public void sendMessage(CustomerOrder order, String orderType) {
        // Kafka stuff here
    }

    // Visible for testing
    protected Message<String> buildMessage(CustomerOrder order, String orderType) throws JsonProcessingException {
        OrderEvent orderEvent = OrderEvent.builder()
                .id(1234)
                .type(orderType)
                .build();

        return MessageBuilder.withPayload(this.mapper.writeValueAsString(orderEvent))
                .setHeader(KafkaHeaders.TOPIC, "orders").setHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }

}
