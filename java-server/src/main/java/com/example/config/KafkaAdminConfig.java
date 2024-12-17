package com.example.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Value("${spring.kafka.sasl.username}")
    private String SASL_USERNAME;

    @Value("${spring.kafka.sasl.password}")
    private String SASL_PASSWORD;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        // Add SASL authentication properties
        configs.put("security.protocol", "SASL_PLAINTEXT");
        configs.put("sasl.mechanism", "SCRAM-SHA-256");
        configs.put("sasl.jaas.config", String.format(
                "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                SASL_USERNAME, SASL_PASSWORD));

        return new KafkaAdmin(configs);
    }

}
