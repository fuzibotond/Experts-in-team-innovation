package com.example.controllers;

import com.example.repository.SensorDataRepository;
import com.example.repository.data.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @GetMapping("/api/data")
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    @GetMapping("/api/data/{sensorType}")
    public List<SensorData> getSensorDataByType(@PathVariable String sensorType) {

        return sensorDataRepository.findAllBySensorType(sensorType);
    }
}
