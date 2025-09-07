package com.example.fishtracking.dto.request;

import com.example.fishtracking.enums.TimeOfDayType;
import com.example.fishtracking.enums.WeatherConditionsType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CatchRequest {
    private Long tackleId;
    private Long lureId;
    private Long locationId;
    private String fishType;
    private Double weight;
    private Double length;
    private TimeOfDayType timeOfDay;
    private LocalDate date;
    private Double waterTemperature;
    private WeatherConditionsType weatherConditions;
    private String notes;
}