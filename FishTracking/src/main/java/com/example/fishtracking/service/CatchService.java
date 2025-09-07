package com.example.fishtracking.service;

import com.example.fishtracking.CatchEvent;
import com.example.fishtracking.dto.request.CatchRequest;
import com.example.fishtracking.dto.response.CatchResponse;
import com.example.fishtracking.dto.response.CatchStatsResponse;
import com.example.fishtracking.entity.*;
import com.example.fishtracking.exception.ResourceNotFoundException;
import com.example.fishtracking.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatchService {
    private final UserRepository userRepository;
    private final CatchRepository catchRepository;
    private final TackleRepository tackleRepository;
    private final LureRepository lureRepository;
    private final LocationRepository locationRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public CatchResponse createCatch(CatchRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Tackle tackle = tackleRepository.findByIdAndUser(request.getTackleId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Tackle not found"));

        Lure lure = lureRepository.findByIdAndUser(request.getLureId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Lure not found"));

        Location location = locationRepository.findByIdAndUser(request.getLocationId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        Catch catchEntity = new Catch();
        catchEntity.setFishType(request.getFishType());
        catchEntity.setWeight(request.getWeight());
        catchEntity.setLength(request.getLength());
        catchEntity.setTimeOfDay(request.getTimeOfDay());
        catchEntity.setDate(request.getDate());
        catchEntity.setWaterTemperature(request.getWaterTemperature());
        catchEntity.setWeatherConditions(request.getWeatherConditions());
        catchEntity.setNotes(request.getNotes());
        catchEntity.setUser(user);
        catchEntity.setTackle(tackle);
        catchEntity.setLure(lure);
        catchEntity.setLocation(location);

        Catch savedCatch = catchRepository.save(catchEntity);

        // Создаем и отправляем событие в Kafka
        CatchEvent event = createCatchEvent(savedCatch, user, tackle, lure, location);
        sendKafkaEvent(event);

        return mapToResponse(savedCatch);
    }

    private CatchEvent createCatchEvent(Catch catchEntity, User user, Tackle tackle, Lure lure, Location location) {
        CatchEvent event = new CatchEvent();
        event.setCatchId(catchEntity.getId());
        event.setUserId(user.getId());
        event.setTackleId(tackle.getId());
        event.setTackleName(tackle.getName());
        event.setTackleType(tackle.getType().name());
        event.setLureId(lure.getId());
        event.setLureName(lure.getName());
        event.setLureType(lure.getType().name());
        event.setLureColor(lure.getColor());
        event.setLocationId(location.getId());
        event.setLocationName(location.getName());
        event.setLocationType(location.getType().name());
        event.setFishType(catchEntity.getFishType());
        event.setWeight(catchEntity.getWeight());
        event.setLength(catchEntity.getLength());
        event.setTimeOfDay(catchEntity.getTimeOfDay());
        event.setDate(catchEntity.getDate());
        event.setWaterTemperature(catchEntity.getWaterTemperature());
        event.setWeatherConditions(catchEntity.getWeatherConditions());

        return event;
    }

    private void sendKafkaEvent(CatchEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("catch-events", message);
            log.info("Отправлено событие об улове в Kafka: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при сериализации события об улове", e);
        }
    }

    public Page<CatchResponse> getAllCatches(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Catch> catches = catchRepository.findByUser(user, pageable);
        return catches.map(this::mapToResponse);
    }

    public CatchResponse getCatchById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Catch catchEntity = catchRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Catch not found with id: " + id));

        return mapToResponse(catchEntity);
    }

    @Transactional
    public CatchResponse updateCatch(Long id, CatchRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Catch catchEntity = catchRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Catch not found with id: " + id));

        Tackle tackle = tackleRepository.findByIdAndUser(request.getTackleId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Tackle not found"));

        Lure lure = lureRepository.findByIdAndUser(request.getLureId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Lure not found"));

        Location location = locationRepository.findByIdAndUser(request.getLocationId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        catchEntity.setFishType(request.getFishType());
        catchEntity.setWeight(request.getWeight());
        catchEntity.setLength(request.getLength());
        catchEntity.setTimeOfDay(request.getTimeOfDay());
        catchEntity.setDate(request.getDate());
        catchEntity.setWaterTemperature(request.getWaterTemperature());
        catchEntity.setWeatherConditions(request.getWeatherConditions());
        catchEntity.setNotes(request.getNotes());
        catchEntity.setTackle(tackle);
        catchEntity.setLure(lure);
        catchEntity.setLocation(location);

        Catch updatedCatch = catchRepository.save(catchEntity);
        return mapToResponse(updatedCatch);
    }

    @Transactional
    public void deleteCatch(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Catch catchEntity = catchRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Catch not found with id: " + id));

        catchRepository.delete(catchEntity);
    }

    public CatchStatsResponse getCatchStatsSummary() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CatchStatsResponse stats = new CatchStatsResponse();
        stats.setTotalCatches(catchRepository.countByUser(user));

        Double totalWeight = catchRepository.sumWeightByUser(user);
        stats.setTotalWeight(totalWeight != null ? totalWeight : 0.0);

        if (stats.getTotalCatches() > 0 && totalWeight != null) {
            stats.setAverageWeight(totalWeight / stats.getTotalCatches());
        } else {
            stats.setAverageWeight(0.0);
        }

        // Статистика по видам рыбы
        List<Object[]> fishTypeStats = catchRepository.countByFishType(user);
        Map<String, Long> fishTypeMap = fishTypeStats.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
        stats.setCatchesByFishType(fishTypeMap);

        // Статистика по местам ловли
        List<Object[]> locationStats = catchRepository.countByLocation(user);
        Map<String, Long> locationMap = locationStats.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
        stats.setCatchesByLocation(locationMap);

        return stats;
    }

    public Map<String, Long> getCatchStatsByFishType() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Object[]> fishTypeStats = catchRepository.countByFishType(user);
        return fishTypeStats.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    public Map<String, Long> getCatchStatsByLocation() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Object[]> locationStats = catchRepository.countByLocation(user);
        return locationStats.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    private CatchResponse mapToResponse(Catch catchEntity) {
        CatchResponse response = new CatchResponse();
        response.setId(catchEntity.getId());
        response.setTackleId(catchEntity.getTackle().getId());
        response.setTackleName(catchEntity.getTackle().getName());
        response.setLureId(catchEntity.getLure().getId());
        response.setLureName(catchEntity.getLure().getName());
        response.setLocationId(catchEntity.getLocation().getId());
        response.setLocationName(catchEntity.getLocation().getName());
        response.setFishType(catchEntity.getFishType());
        response.setWeight(catchEntity.getWeight());
        response.setLength(catchEntity.getLength());
        response.setTimeOfDay(catchEntity.getTimeOfDay());
        response.setDate(catchEntity.getDate());
        response.setWaterTemperature(catchEntity.getWaterTemperature());
        response.setWeatherConditions(catchEntity.getWeatherConditions());
        response.setNotes(catchEntity.getNotes());
        return response;
    }
}