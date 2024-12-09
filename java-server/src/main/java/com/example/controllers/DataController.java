package com.example.controllers;

import com.example.dto.ResponseDTO;
import com.example.repository.SensorDataRepository;
import com.example.repository.data.SensorData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final SensorDataRepository sensorDataRepository;

    public DataController(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    @GetMapping("/{sensorType}")
    public List<SensorData> getSensorDataByType(@PathVariable String sensorType) {
        return sensorDataRepository.findAllBySensorType(sensorType);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDTO<String>> deleteSensorData(@RequestParam String id) {
        try {
            sensorDataRepository.deleteById(id);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ResponseDTO<>("Unable to delete data with id: " + id));
        }

        return ResponseEntity.ok(new ResponseDTO<>("Data has been deleted successfully with id: " + id));
    }

    @DeleteMapping("/remove/all")
    public ResponseEntity<ResponseDTO<String>> deleteSensorData() {
        try {
            sensorDataRepository.deleteAll();
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ResponseDTO<>("Unable to delete all data:"));
        }

        return ResponseEntity.ok(new ResponseDTO<>("All data has been deleted successfully!"));
    }
}
