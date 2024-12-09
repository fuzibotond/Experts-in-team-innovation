package com.example.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEdgeDeviceDto {

    private String topic;
    private String username;
    private String password;

}
