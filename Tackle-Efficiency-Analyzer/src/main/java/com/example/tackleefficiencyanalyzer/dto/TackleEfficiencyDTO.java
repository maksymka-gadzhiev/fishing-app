package com.example.tackleefficiencyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TackleEfficiencyDTO {
    private Long tackleId;
    private String tackleName;
    private String tackleType;
    private Long totalCatches;
    private Double averageWeight;
}
