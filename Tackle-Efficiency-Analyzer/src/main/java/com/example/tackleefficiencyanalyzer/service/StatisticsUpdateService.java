package com.example.tackleefficiencyanalyzer.service;

import com.example.tackleefficiencyanalyzer.entity.CatchEvent;
import com.example.tackleefficiencyanalyzer.entity.LureStatistics;
import com.example.tackleefficiencyanalyzer.entity.TackleStatistics;
import com.example.tackleefficiencyanalyzer.repository.LureStatisticsRepository;
import com.example.tackleefficiencyanalyzer.repository.TackleStatisticsRepository;
import com.example.tackleefficiencyanalyzer.repository.TimeStatisticsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.tackleefficiencyanalyzer.entity.TimeStatistics;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsUpdateService {
    private final TackleStatisticsRepository tackleStatsRepository;
    private final LureStatisticsRepository lureStatsRepository;
    private final TimeStatisticsRepository timeStatsRepository;

    @Transactional
    public void updateStatistics(CatchEvent catchEvent) {
        updateTackleStatistics(catchEvent);
        updateLureStatistics(catchEvent);
        updateTimeStatistics(catchEvent);
    }

    private void updateTackleStatistics(CatchEvent catchEvent) {
        TackleStatistics stats = tackleStatsRepository
                .findByUserIdAndTackleIdAndFishType(
                        catchEvent.getUserId(),
                        catchEvent.getTackleId(),
                        catchEvent.getFishType()
                )
                .orElse(new TackleStatistics(
                        null,
                        catchEvent.getUserId(),
                        catchEvent.getTackleId(),
                        catchEvent.getFishType(),
                        0,
                        0.0,
                        0.0,
                        null
                ));

        stats.setTotalCatches(stats.getTotalCatches() + 1);
        if (catchEvent.getWeight() != null) {
            stats.setTotalWeight(stats.getTotalWeight() + catchEvent.getWeight());
        }

        tackleStatsRepository.save(stats);
    }

    private void updateLureStatistics(CatchEvent catchEvent) {
        LureStatistics stats = lureStatsRepository
                .findByUserIdAndLureIdAndFishType(
                        catchEvent.getUserId(),
                        catchEvent.getLureId(),
                        catchEvent.getFishType()
                )
                .orElse(new LureStatistics(
                        null,
                        catchEvent.getUserId(),
                        catchEvent.getLureId(),
                        catchEvent.getFishType(),
                        catchEvent.getLureType(),  // Добавлено
                        catchEvent.getLureColor(), // Добавлено
                        catchEvent.getWaterTemperature(),
                        0,
                        null
                ));

        stats.setTotalCatches(stats.getTotalCatches() + 1);

        // Обновляем тип и цвет приманки, если они изменились
        if (catchEvent.getLureType() != null) {
            stats.setLureType(catchEvent.getLureType());
        }
        if (catchEvent.getLureColor() != null) {
            stats.setLureColor(catchEvent.getLureColor());
        }

        lureStatsRepository.save(stats);
    }

    private void updateTimeStatistics(CatchEvent catchEvent) {
        TimeStatistics stats = timeStatsRepository
                .findByUserIdAndFishTypeAndTimeOfDay(
                        catchEvent.getUserId(),
                        catchEvent.getFishType(),
                        catchEvent.getTimeOfDay()
                )
                .orElse(new TimeStatistics(
                        null,
                        catchEvent.getUserId(),
                        catchEvent.getFishType(),
                        catchEvent.getTimeOfDay(),
                        0,
                        null
                ));

        stats.setTotalCatches(stats.getTotalCatches() + 1);
        timeStatsRepository.save(stats);
    }
}