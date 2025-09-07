package com.example.fishtracking.dto.request;

import com.example.fishtracking.enums.LocationType;
import lombok.Data;

@Data
public class LocationRequest {
    private String name;
    private String description;
    private String coordinates;
    private LocationType type;
}