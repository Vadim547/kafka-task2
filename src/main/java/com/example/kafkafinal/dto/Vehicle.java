package com.example.kafkafinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String id;
    private Double latitude;
    private Double longitude;
    private Double distance = 0.0;

}
