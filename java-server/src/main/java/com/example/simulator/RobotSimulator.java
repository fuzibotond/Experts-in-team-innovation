package com.example.simulator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
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
        String topic = "robot/data-1";

        for (int i = 0; i < 5; i++) {
            String data = "{\"sensorType\": \"new\",\"topic\": \""+topic+"\", \"data\": " + (20 + Math.random() * 5) + ", \"timestamp\": \"" + LocalDateTime.now() + "\"}";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
            producer.send(record);
        }
        producer.close();
    }
}
