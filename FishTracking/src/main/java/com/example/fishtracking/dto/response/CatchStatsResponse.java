package com.example.fishtracking.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class CatchStatsResponse {
    private Long totalCatches;
    private Double totalWeight;
    private Double averageWeight;
    private Map<String, Long> catchesByFishType;
    private Map<String, Long> catchesByLocation;
}