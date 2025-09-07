package com.example.tackleefficiencyanalyzer.repository;

import com.example.tackleefficiencyanalyzer.entity.CatchEvent;
import com.example.tackleefficiencyanalyzer.enums.WeatherConditionsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatchEventRepository extends JpaRepository<CatchEvent, Long> {
    List<CatchEvent> findByUserId(Long userId);
    List<CatchEvent> findByFishType(String fishType);
    List<CatchEvent> findByUserIdAndFishType(Long userId, String fishType);
    List<CatchEvent> findByWeatherConditions(WeatherConditionsType weatherConditions);
    List<CatchEvent> findByLocationName(String locationName);
}
