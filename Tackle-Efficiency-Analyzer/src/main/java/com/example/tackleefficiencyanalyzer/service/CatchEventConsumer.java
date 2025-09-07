package com.example.tackleefficiencyanalyzer.service;

import com.example.tackleefficiencyanalyzer.entity.CatchEvent;
import com.example.tackleefficiencyanalyzer.repository.CatchEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatchEventConsumer {
    private final CatchEventRepository catchEventRepository;
    private final StatisticsUpdateService statisticsUpdateService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "catch-events", groupId = "tackle-analyzer-group")
    public void consume(String message) {
        try {
            CatchEvent catchEvent = objectMapper.readValue(message, CatchEvent.class);
            catchEventRepository.save(catchEvent);
            statisticsUpdateService.updateStatistics(catchEvent);
            log.info("Обработано событие об улове: {}", catchEvent);
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: {}", message, e);
        }
    }
}