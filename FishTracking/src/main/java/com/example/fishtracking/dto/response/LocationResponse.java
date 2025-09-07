package com.example.fishtracking.dto.response;

import com.example.fishtracking.enums.LocationType;
import lombok.Data;

@Data
public class LocationResponse {
    private Long id;
    private String name;
    private String description;
    private String coordinates;
    private LocationType type;
}