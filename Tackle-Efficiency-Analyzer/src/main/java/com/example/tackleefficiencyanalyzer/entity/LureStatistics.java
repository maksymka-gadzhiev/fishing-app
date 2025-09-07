package com.example.tackleefficiencyanalyzer.entity;

import com.example.tackleefficiencyanalyzer.enums.LureType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "lure_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LureStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lure_id")
    private Long lureId;

    @Column(name = "fish_type")
    private String fishType;

    @Enumerated(EnumType.STRING)
    @Column(name = "lure_type")
    private LureType lureType;

    @Column(name = "lure_color")
    private String lureColor;

    @Column(name = "water_temperature")
    private Double waterTemperature;

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