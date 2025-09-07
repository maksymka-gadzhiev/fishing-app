package com.example.tackleefficiencyanalyzer.entity;

import com.example.tackleefficiencyanalyzer.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "catch_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catch_id")
    private Long catchId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tackle_id")
    private Long tackleId;

    @Column(name = "tackle_name")
    private String tackleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "tackle_type")
    private TackleType tackleType;

    @Column(name = "lure_id")
    private Long lureId;

    @Column(name = "lure_name")
    private String lureName;

    @Enumerated(EnumType.STRING)
    @Column(name = "lure_type")
    private LureType lureType;

    @Column(name = "lure_color")
    private String lureColor;

    @Column(name = "fish_type")
    private String fishType;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "length")
    private Double length;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day")
    private TimeOfDayType timeOfDay;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "water_temperature")
    private Double waterTemperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "weather_conditions")
    private WeatherConditionsType weatherConditions;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name")
    private String locationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private LocationType locationType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}