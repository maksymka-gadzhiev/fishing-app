package com.example.fishtracking.dto.response;

import com.example.fishtracking.enums.TimeOfDayType;
import com.example.fishtracking.enums.WeatherConditionsType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CatchResponse {
    private Long id;
    private Long tackleId;
    private String tackleName;
    private Long lureId;
    private String lureName;
    private Long locationId;
    private String locationName;
    private String fishType;
    private Double weight;
    private Double length;
    private TimeOfDayType timeOfDay;
    private LocalDate date;
    private Double waterTemperature;
    private WeatherConditionsType weatherConditions;
    private String notes;
}