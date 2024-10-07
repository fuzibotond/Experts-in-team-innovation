package com.example.repository;

import com.example.repository.data.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {
    List<SensorData> findAllBySensorType(String sensorType);
}

