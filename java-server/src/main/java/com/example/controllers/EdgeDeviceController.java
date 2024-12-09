package com.example.controllers;

import com.example.dto.DynamicTopicDTO;
import com.example.dto.RegisterEdgeDeviceDto;
import com.example.dto.ResponseDTO;
import com.example.dto.EdgeDeviceDto;
import com.example.repository.EdgeDeviceRepository;
import com.example.repository.data.EdgeDevice;
import com.example.service.DynamicKafkaListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/edge-device")
public class EdgeDeviceController {

    private final EdgeDeviceRepository edgeDeviceRepository;
    private final DynamicKafkaListenerService kafkaListenerService;

    @Autowired
    public EdgeDeviceController(
            final EdgeDeviceRepository edgeDeviceRepository,
            final DynamicKafkaListenerService kafkaListenerService) {
        this.edgeDeviceRepository = edgeDeviceRepository;
        this.kafkaListenerService = kafkaListenerService;
    }

    // Add a new topic
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<DynamicTopicDTO>> addTopic(@RequestBody RegisterEdgeDeviceDto dto) {
        Optional<EdgeDevice> existingTopic = edgeDeviceRepository.findByTopic(dto.getTopic())
                .stream()
                .findFirst();

        if (existingTopic.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>("Topic already exists", null));
        }

        final EdgeDevice edgeDevice = kafkaListenerService.addTopic(dto.getTopic());

        return ResponseEntity.ok(new ResponseDTO<>(
                "Topic added successfully",
                new DynamicTopicDTO(edgeDevice.getId(), edgeDevice.getTopic())
        ));
    }

    @PostMapping("/subscribe-all")
    public ResponseEntity<ResponseDTO> subscribeToAllListeners() {
        Set<String> topics = kafkaListenerService.subscribeToAllTopics();
        return ResponseEntity.ok(new ResponseDTO<>("Subscribed to all available topics.", topics));
    }

    // Remove a topic
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDTO<String>> removeTopic(@RequestParam String id) {
        Optional<EdgeDevice> existingTopic = edgeDeviceRepository.findById(id);

        if (existingTopic.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>("Topic with ID not found", null));
        }

        kafkaListenerService.removeTopic(id);
        return ResponseEntity.ok(new ResponseDTO<>("Topic removed successfully", id));
    }

    // Get all active topics
    @GetMapping("/active/list")
    public ResponseEntity<ResponseDTO<Set<String>>> getActiveTopicsList() {
        Set<String> activeTopics = kafkaListenerService.getActiveTopics();
        return ResponseEntity.ok(new ResponseDTO<>("Active topics fetched", activeTopics));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DynamicTopicDTO> getTopicById(@PathVariable String id) {
        Optional<EdgeDevice> topicById = kafkaListenerService.getTopicById(id);

        if (topicById.isPresent()) {
            EdgeDevice mapping = topicById.get();
            return ResponseEntity.ok(new DynamicTopicDTO(mapping.getId(), mapping.getTopic()));
        }

        return ResponseEntity.notFound().build();
    }

    // Check if a topic is used
    @GetMapping("/find/{topic}")
    public ResponseEntity<List<EdgeDeviceDto>> getDevicesByTopic(@PathVariable String topic) {
        List<EdgeDevice> mappings = edgeDeviceRepository.findByTopic(topic);
        return ResponseEntity.ok(mappings.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }

    // Get all topic-device mappings
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<EdgeDeviceDto>>> getAllTopics() {
        List<EdgeDevice> mappings = edgeDeviceRepository.findAll();
        List<EdgeDeviceDto> dtoList = mappings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseDTO<>("Fetched all topics", dtoList));
    }

    // Convert Entity to DTO
    private EdgeDeviceDto convertToDTO(EdgeDevice mapping) {
        return EdgeDeviceDto.builder()
                .id(mapping.getId())
                .topic(mapping.getTopic())
                .lastUsed(mapping.getLastUsed())
                .username(mapping.getUsername())
                .password(mapping.getPassword())
                .createdBy(mapping.getCreatedBy())
                .build();
    }
}
