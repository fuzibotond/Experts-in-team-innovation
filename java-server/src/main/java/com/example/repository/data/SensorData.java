package com.example.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "sensorData")
public class SensorData {
    @Id
    private String id;
    private String topic;
    private String sensorType;
    private String data;
    private LocalDateTime timestamp;
}
