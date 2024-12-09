package com.example.simulator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class RobotSimulator {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9093"); // Broker address
        properties.put("security.protocol", "SSL"); // Use SSL connection
        properties.put("ssl.keystore.location", "/path/to/kafka.keystore.jks"); // Keystore path
        properties.put("ssl.keystore.password", "serversecret"); // Keystore password
        properties.put("ssl.key.password", "serversecret"); // Key password
        properties.put("ssl.truststore.location", "/path/to/kafka.truststore.jks"); // Truststore path
        properties.put("ssl.truststore.password", "serversecret"); // Truststore password
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String topic = "test";

        for (int i = 0; i < 10; i++) {
            String data = "{\"sensorType\": \"temperature\", \"value\": " + (20 + Math.random() * 5) + ", \"timestamp\": \"2024-10-03T12:30:00\"}";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
            producer.send(record);
        }
        producer.close();
    }


}
