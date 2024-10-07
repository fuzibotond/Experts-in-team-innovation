package com.example.simulator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class RobotSimulator {
    public static void main(String[] args) {
        // Kafka producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Simulating sensor data from robot
        String topic = "sensor-data-topic";

        for (int i = 0; i < 10; i++) {
            String data = "{\"sensorType\": \"temperature\", \"value\": " + (20 + Math.random() * 5) + ", \"timestamp\": \"2024-10-03T12:30:00\"}";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
            producer.send(record);
        }

        producer.close();
    }
}
