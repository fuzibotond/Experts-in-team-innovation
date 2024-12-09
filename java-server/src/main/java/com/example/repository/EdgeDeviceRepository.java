package com.example.repository;

import com.example.repository.data.EdgeDevice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EdgeDeviceRepository extends MongoRepository<EdgeDevice, String> {
    List<EdgeDevice> findByTopic(String topic);
    boolean existsByTopic(String topic);

}
