package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdgeDeviceDto {
    private String id;
    private String topic;
    private String username;
    private String password;
    private String createdBy;
    private LocalDateTime lastUsed;
}
