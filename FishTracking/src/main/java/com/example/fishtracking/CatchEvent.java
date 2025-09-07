package com.example.fishtracking;

import com.example.fishtracking.enums.TimeOfDayType;
import com.example.fishtracking.enums.WeatherConditionsType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CatchEvent {
    private Long catchId;
    private Long userId;
    private Long tackleId;
    private String tackleName;
    private String tackleType;
    private Long lureId;
    private String lureName;
    private String lureType;
    private String lureColor;
    private Long locationId;
    private String locationName;
    private String locationType;
    private String fishType;
    private Double weight;
    private Double length;
    private TimeOfDayType timeOfDay;
    private LocalDate date;
    private Double waterTemperature;
    private WeatherConditionsType weatherConditions;
}
