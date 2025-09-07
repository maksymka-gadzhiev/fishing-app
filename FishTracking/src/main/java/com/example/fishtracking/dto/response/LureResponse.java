package com.example.fishtracking.dto.response;

import com.example.fishtracking.enums.LureType;
import lombok.Data;

@Data
public class LureResponse {
    private Long id;
    private String name;
    private LureType type;
    private String color;
    private Double size;
    private Double weight;
}

