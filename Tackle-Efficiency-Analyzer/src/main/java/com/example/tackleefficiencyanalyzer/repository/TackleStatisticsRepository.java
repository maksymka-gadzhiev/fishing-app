package com.example.tackleefficiencyanalyzer.repository;

import com.example.tackleefficiencyanalyzer.entity.TackleStatistics;
import com.example.tackleefficiencyanalyzer.enums.TackleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TackleStatisticsRepository extends JpaRepository<TackleStatistics, Long> {
    List<TackleStatistics> findByUserId(Long userId);
    List<TackleStatistics> findByUserIdAndFishType(Long userId, String fishType);
    Optional<TackleStatistics> findByUserIdAndTackleIdAndFishType(Long userId, Long tackleId, String fishType);
}