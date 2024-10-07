package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class SensorDataController {

    @GetMapping("/api/sensor-data")
    public ResponseEntity<String> getSensorData() {
        // Logic to get consumed Kafka data (if stored in DB or cache)
        // For simplicity, returning a static response here
        String sensorData = "{\"sensorType\": \"temperature\", \"value\": 22.5, \"timestamp\": \"2024-10-03T12:30:00\"}";
        return ResponseEntity.ok(sensorData);
    }
}
