package com.example.service;

import com.example.config.KafkaConsumerConfig;
import com.example.dto.EdgeDeviceDto;
import com.example.dto.RegisterEdgeDeviceDto;
import com.example.repository.SensorDataRepository;
import com.example.repository.EdgeDeviceRepository;
import com.example.repository.data.SensorData;
import com.example.repository.data.EdgeDevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class DynamicKafkaListenerService {

    private final KafkaAdmin kafkaAdmin;
    private final ObjectMapper objectMapper;
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final SimpMessagingTemplate messagingTemplate;
    private final SensorDataRepository sensorDataRepository;
    private final Set<String> activeTopics = new HashSet<>();
    private final EdgeDeviceRepository edgeDeviceRepository;
    private final Map<String, ConcurrentMessageListenerContainer<String, String>> containers = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DynamicKafkaListenerService.class);

    @Autowired
    public DynamicKafkaListenerService(final KafkaAdmin kafkaAdmin,
                                       final KafkaConsumerConfig kafkaConsumerConfig,
                                       final SensorDataRepository sensorDataRepository,
                                       final SimpMessagingTemplate messagingTemplate,
                                       final EdgeDeviceRepository edgeDeviceRepository) {
        this.edgeDeviceRepository = edgeDeviceRepository;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.sensorDataRepository = sensorDataRepository;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = new ObjectMapper();
        this.kafkaAdmin = kafkaAdmin;

    }

    public Set<String> subscribeToAllTopics() {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            ListTopicsResult topicsResult = adminClient.listTopics();
            Set<String> topics = topicsResult.names().get();

            for (String topic : topics) {
                if (!activeTopics.contains(topic)) {
                    logger.info("Subscribing to topic: {}", topic);
                    addTopic(topic); // Add topic dynamically
                } else {
                    logger.info("Already subscribed to topic: {}", topic);
                }
            }
            return topics;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error fetching topics from Kafka", e);
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
        return new HashSet<>();
    }

    // Method to dynamically add a topic
    public synchronized EdgeDevice addTopic(String topic) {

        if (activeTopics.contains(topic)) {
            logger.warn("Topic {} is already being listened to.", topic);
            return null;
        }

        logger.info("Adding topic {} to listeners.", topic);
        EdgeDevice temp = null;
        // Check if the topic already exists in the database
        if (!edgeDeviceRepository.existsByTopic(topic)) {
            final EdgeDevice edgeDevice = new EdgeDevice();
            edgeDevice.setTopic(topic);
            edgeDevice.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            edgeDevice.setLastUsed(LocalDateTime.now());
            edgeDeviceRepository.save(edgeDevice);
            logger.info("Topic {} saved to database.", topic);

            temp = edgeDevice;
        }

        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener((MessageListener<String, String>) record -> {
            try {
                logger.info("Received message from topic {}: {}", topic, record.value());
                saveDataByTopic(topic, record.value());
            } catch (Exception e) {
                logger.error("Malformed message received from topic {}: {}", topic, record.value(), e);
            }
        });


        logger.info("Starting listener for topic: {}", topic);
        ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(
                kafkaConsumerConfig.consumerFactory(), containerProps);
        container.start();
        logger.info("Listener for topic {} started successfully.", topic);

        containers.put(topic, container);
        activeTopics.add(topic);
        return temp;
    }


    // Method to remove a topic
    public synchronized void removeTopic(String id) {
        Optional<EdgeDevice> topicById = edgeDeviceRepository.findById(id);
        if (topicById.isPresent()) {
            String topicName = topicById.get().getTopic();

            // Stop and remove the listener container
            ConcurrentMessageListenerContainer<String, String> container = containers.remove(topicName);
            if (container != null) {
                logger.info("Stopping and removing listener for topic: {}", topicName);
                container.stop();
            }

            // Remove from activeTopics
            activeTopics.remove(topicName);

            // Delete the topic from the database
            edgeDeviceRepository.deleteById(id);
            deleteKafkaTopic(topicName);
            logger.info("Topic {} removed successfully.", topicName);
        } else {
            logger.warn("Topic with ID {} not found. Skipping removal.", id);
        }
    }


    // Method to get active topics
    public Set<String> getActiveTopics() {
        return activeTopics;
    }

    // Save received data by topic to MongoDB
    private void saveDataByTopic(String topic, String data) {

        try {
            // Parse the JSON string to a Map
            Map<String, Object> parsedData = objectMapper.readValue(data, Map.class);

            // Create a new SensorData object and map fields from the parsedData
            SensorData sensorData = new SensorData();
            sensorData.setTopic((topic));
            sensorData.setSensorType((String) parsedData.get("sensorType"));
            sensorData.setData(parsedData.get("data").toString());  // Ensure value is Double type
            sensorData.setTimestamp(LocalDateTime.now());  // Use current time for timestamp

            // Log before saving to MongoDB
            logger.info("Saving message to MongoDB: {}", sensorData);
            sensorDataRepository.save(sensorData);

            // Send real-time update to clients
            messagingTemplate.convertAndSend("/topic/sensor-data", data);

            // Log after successful save
            logger.info("Successfully saved message to MongoDB: {}", sensorData);
            // Update last used timestamp for the topic
            EdgeDevice mapping = edgeDeviceRepository.findByTopic(topic)
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (mapping != null) {
                mapping.setLastUsed(LocalDateTime.now());
                edgeDeviceRepository.save(mapping);
            }
        } catch (Exception e) {
            logger.error("Error while processing message: {}", data, e);
        }
    }

    public Optional<EdgeDevice> getTopicById(String id) {
        return edgeDeviceRepository.findById(id);
    }

    public void deleteKafkaTopic(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            adminClient.deleteTopics(Collections.singleton(topicName)).all().get();
            logger.info("Kafka topic {} deleted successfully.", topicName);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error deleting Kafka topic: {}", topicName, e);
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
    }

}
