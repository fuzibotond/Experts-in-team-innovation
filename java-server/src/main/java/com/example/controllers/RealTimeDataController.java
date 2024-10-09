package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeDataController {
    private final SimpMessagingTemplate messagingTemplate;

    public RealTimeDataController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "sensor-data-topic", groupId = "sensor-consumer-group")
    public void listenToKafka(String message) {
        // Send the received message to WebSocket clients
        messagingTemplate.convertAndSend("/topic/sensor-data", message);
    }
}