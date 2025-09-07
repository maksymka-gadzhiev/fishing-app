package com.example.fishtracking.dto.request;

import com.example.fishtracking.enums.LureType;
import lombok.Data;

@Data
public class LureRequest {
    private String name;
    private LureType type;
    private String color;
    private Double size;
    private Double weight;
}
