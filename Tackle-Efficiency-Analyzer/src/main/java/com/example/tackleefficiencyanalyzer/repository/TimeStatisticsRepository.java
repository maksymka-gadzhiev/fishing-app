package com.example.tackleefficiencyanalyzer.repository;

import com.example.tackleefficiencyanalyzer.entity.TimeStatistics;
import com.example.tackleefficiencyanalyzer.enums.TimeOfDayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeStatisticsRepository extends JpaRepository<TimeStatistics, Long> {
    List<TimeStatistics> findByUserId(Long userId);
    List<TimeStatistics> findByUserIdAndFishType(Long userId, String fishType);
    Optional<TimeStatistics> findByUserIdAndFishTypeAndTimeOfDay(Long userId, String fishType, TimeOfDayType timeOfDay);
}
