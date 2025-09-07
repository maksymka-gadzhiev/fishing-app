package com.example.tackleefficiencyanalyzer.entity;

import com.example.tackleefficiencyanalyzer.enums.TimeOfDayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "fish_type")
    private String fishType;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day")
    private TimeOfDayType timeOfDay;

    @Column(name = "total_catches")
    private Integer totalCatches = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
