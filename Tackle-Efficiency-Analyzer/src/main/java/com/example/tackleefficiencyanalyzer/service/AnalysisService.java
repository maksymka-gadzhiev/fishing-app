package com.example.tackleefficiencyanalyzer.service;

import com.example.tackleefficiencyanalyzer.dto.LureEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.dto.TackleEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.dto.TimeEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.entity.CatchEvent;
import com.example.tackleefficiencyanalyzer.entity.LureStatistics;
import com.example.tackleefficiencyanalyzer.entity.TackleStatistics;
import com.example.tackleefficiencyanalyzer.entity.TimeStatistics;
import com.example.tackleefficiencyanalyzer.enums.WeatherConditionsType;
import com.example.tackleefficiencyanalyzer.repository.CatchEventRepository;
import com.example.tackleefficiencyanalyzer.repository.LureStatisticsRepository;
import com.example.tackleefficiencyanalyzer.repository.TackleStatisticsRepository;
import com.example.tackleefficiencyanalyzer.repository.TimeStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {

    private final CatchEventRepository catchEventRepository;
    private final TackleStatisticsRepository tackleStatsRepository;
    private final LureStatisticsRepository lureStatsRepository;
    private final TimeStatisticsRepository timeStatsRepository;

    public List<TackleEfficiencyDTO> getTackleEfficiency(String fishType, Long userId) {
        List<TackleStatistics> stats;

        if (userId != null && fishType != null) {
            stats = tackleStatsRepository.findByUserIdAndFishType(userId, fishType);
        } else if (userId != null) {
            stats = tackleStatsRepository.findByUserId(userId);
        } else if (fishType != null) {
            stats = Collections.emptyList();
        } else {
            stats = tackleStatsRepository.findAll();
        }

        return stats.stream()
                .map(stat -> new TackleEfficiencyDTO(
                        stat.getTackleId(),
                        "Tackle Name", // Заглушка, так как у нас нет этого поля
                        "UNKNOWN",     // Заглушка вместо типа снасти
                        stat.getTotalCatches().longValue(),
                        stat.getAverageWeight()
                ))
                .collect(Collectors.toList());
    }

    public List<TimeEfficiencyDTO> getTimeEfficiency(String fishType, Long userId) {
        List<TimeStatistics> stats;

        if (userId != null && fishType != null) {
            stats = timeStatsRepository.findByUserIdAndFishType(userId, fishType);
        } else if (userId != null) {
            stats = timeStatsRepository.findByUserId(userId);
        } else if (fishType != null) {
            // Для поиска по типу рыбы без указания пользователя
            stats = Collections.emptyList();
        } else {
            stats = timeStatsRepository.findAll();
        }

        return stats.stream()
                .map(stat -> new TimeEfficiencyDTO(
                        stat.getTimeOfDay(),
                        stat.getTotalCatches().longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<LureEfficiencyDTO> getLureEfficiency(String fishType, Long userId, Double waterTemperature) {
        List<LureStatistics> stats;

        if (userId != null && fishType != null) {
            stats = lureStatsRepository.findByUserIdAndFishType(userId, fishType);
        } else if (userId != null) {
            stats = lureStatsRepository.findByUserId(userId);
        } else if (fishType != null) {
            // Для поиска по типу рыбы без указания пользователя
            stats = Collections.emptyList();
        } else {
            stats = lureStatsRepository.findAll();
        }

        // Фильтрация по температуре воды, если указана
        if (waterTemperature != null) {
            stats = stats.stream()
                    .filter(stat -> stat.getWaterTemperature() != null &&
                            Math.abs(stat.getWaterTemperature() - waterTemperature) <= 2.0)
                    .collect(Collectors.toList());
        }

        return stats.stream()
                .map(stat -> new LureEfficiencyDTO(
                        stat.getLureId(),
                        "Lure Name", // Здесь нужно будет получить название приманки из базы
                        stat.getLureType() != null ? stat.getLureType().name() : "UNKNOWN",
                        stat.getLureColor(),
                        stat.getTotalCatches().longValue()
                ))
                .collect(Collectors.toList());
    }

    // Дополнительные методы для анализа условий ловли
    public Map<WeatherConditionsType, Long> getWeatherConditionsEfficiency(String fishType, Long userId) {
        List<CatchEvent> events;

        if (userId != null && fishType != null) {
            events = catchEventRepository.findByUserIdAndFishType(userId, fishType);
        } else if (userId != null) {
            events = catchEventRepository.findByUserId(userId);
        } else if (fishType != null) {
            events = catchEventRepository.findByFishType(fishType);
        } else {
            events = catchEventRepository.findAll();
        }

        return events.stream()
                .filter(event -> event.getWeatherConditions() != null)
                .collect(Collectors.groupingBy(
                        CatchEvent::getWeatherConditions,
                        Collectors.counting()
                ));
    }

    // Метод для получения лучших мест ловли
    public Map<String, Long> getLocationEfficiency(String fishType, Long userId) {
        List<CatchEvent> events;

        if (userId != null && fishType != null) {
            events = catchEventRepository.findByUserIdAndFishType(userId, fishType);
        } else if (userId != null) {
            events = catchEventRepository.findByUserId(userId);
        } else if (fishType != null) {
            events = catchEventRepository.findByFishType(fishType);
        } else {
            events = catchEventRepository.findAll();
        }

        return events.stream()
                .filter(event -> event.getLocationName() != null)
                .collect(Collectors.groupingBy(
                        CatchEvent::getLocationName,
                        Collectors.counting()
                ));
    }
}
