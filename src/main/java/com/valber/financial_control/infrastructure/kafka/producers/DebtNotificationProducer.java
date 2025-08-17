package com.valber.financial_control.infrastructure.kafka.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valber.financial_control.application.kafka.messages.DebtNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DebtNotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(DebtNotificationProducer.class);
    private static final String TOPIC_NAME = "financial-control-notification";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public DebtNotificationProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendDebtNotification(DebtNotificationMessage message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(TOPIC_NAME, message.getUserId(), messageJson);
            logger.info("Sent debt notification to Kafka: {}", messageJson);
        } catch (JsonProcessingException e) {
            logger.error("Error converting message to JSON: {}", message, e);
        }
    }
}
