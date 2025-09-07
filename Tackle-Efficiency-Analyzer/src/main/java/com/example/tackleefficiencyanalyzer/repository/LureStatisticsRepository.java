package com.example.tackleefficiencyanalyzer.repository;

import com.example.tackleefficiencyanalyzer.entity.LureStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LureStatisticsRepository extends JpaRepository<LureStatistics, Long> {
    List<LureStatistics> findByUserId(Long userId);
    List<LureStatistics> findByUserIdAndFishType(Long userId, String fishType);
    Optional<LureStatistics> findByUserIdAndLureIdAndFishType(Long userId, Long lureId, String fishType);
}