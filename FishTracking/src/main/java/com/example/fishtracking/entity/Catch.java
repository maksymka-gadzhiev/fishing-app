package com.example.fishtracking.entity;

import com.example.fishtracking.enums.TimeOfDayType;
import com.example.fishtracking.enums.WeatherConditionsType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "catches")
public class Catch {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fish_type")
    private String fishType;

    private Double weight;
    private Double length;

    @Column(name = "time_of_day")
    @Enumerated(EnumType.STRING)
    private TimeOfDayType timeOfDay;

    private LocalDate date;

    @Column(name = "water_temperature")
    private Double waterTemperature;

    @Column(name = "weather_conditions")
    @Enumerated(EnumType.STRING)
    private WeatherConditionsType weatherConditions;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tackle_id")
    private Tackle tackle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lure_id")
    private Lure lure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
}
