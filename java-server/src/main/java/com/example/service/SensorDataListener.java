package com.example.service;

import com.example.repository.SensorDataRepository;
import com.example.repository.data.SensorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SensorDataListener {

    private static final Logger logger = LoggerFactory.getLogger(SensorDataListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();  // Jackson ObjectMapper


    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "sensor-data-topic", groupId = "sensor-group")
    public void listen(String message) {
        logger.info("Received message: {}", message);


        try {
            // Parse the JSON string to a Map
            Map<String, Object> parsedData = objectMapper.readValue(message, Map.class);

            // Create a new SensorData object and map fields from the parsedData
            SensorData sensorData = new SensorData();
            sensorData.setSensorType((String) parsedData.get("sensorType"));
            sensorData.setData((Double) parsedData.get("value"));  // Ensure value is Double type
            sensorData.setTimestamp(LocalDateTime.now());  // Use current time for timestamp

            // Log before saving to MongoDB
            logger.info("Saving message to MongoDB: {}", sensorData);
            sensorDataRepository.save(sensorData);

            // Send real-time update to clients
            messagingTemplate.convertAndSend("/topic/sensor-data", message);

            // Log after successful save
            logger.info("Successfully saved message to MongoDB: {}", sensorData);
        } catch (Exception e) {
            logger.error("Error while processing message: {}", message, e);
        }
    }
}
