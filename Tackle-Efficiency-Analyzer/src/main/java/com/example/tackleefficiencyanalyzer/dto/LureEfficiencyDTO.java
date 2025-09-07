package com.example.tackleefficiencyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LureEfficiencyDTO {
    private Long lureId;
    private String lureName;
    private String lureType;
    private String lureColor;
    private Long totalCatches;
}
