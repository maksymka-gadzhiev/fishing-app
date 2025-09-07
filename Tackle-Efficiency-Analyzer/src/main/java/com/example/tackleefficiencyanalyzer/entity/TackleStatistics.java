package com.example.tackleefficiencyanalyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tackle_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TackleStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tackle_id")
    private Long tackleId;

    @Column(name = "fish_type")
    private String fishType;

    @Column(name = "total_catches")
    private Integer totalCatches = 0;

    @Column(name = "total_weight")
    private Double totalWeight;

    @Column(name = "average_weight")
    private Double averageWeight;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();

        if (totalCatches > 0 && totalWeight > 0) {
            averageWeight = totalWeight / totalCatches;
        }
    }
}