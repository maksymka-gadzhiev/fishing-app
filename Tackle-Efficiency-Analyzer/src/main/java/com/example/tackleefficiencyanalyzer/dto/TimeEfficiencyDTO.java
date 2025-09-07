package com.example.tackleefficiencyanalyzer.dto;

import com.example.tackleefficiencyanalyzer.enums.TimeOfDayType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeEfficiencyDTO {
    private TimeOfDayType timeOfDay;
    private Long catchCount;
}
