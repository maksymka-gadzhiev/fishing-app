package com.example.tackleefficiencyanalyzer.controller;

import com.example.tackleefficiencyanalyzer.dto.LureEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.dto.TackleEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.dto.TimeEfficiencyDTO;
import com.example.tackleefficiencyanalyzer.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping("/tackle")
    public ResponseEntity<List<TackleEfficiencyDTO>> getTackleEfficiency(
            @RequestParam(required = false) String fishType,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(analysisService.getTackleEfficiency(fishType, userId));
    }

    @GetMapping("/time")
    public ResponseEntity<List<TimeEfficiencyDTO>> getTimeEfficiency(
            @RequestParam(required = false) String fishType,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(analysisService.getTimeEfficiency(fishType, userId));
    }

    @GetMapping("/lure")
    public ResponseEntity<List<LureEfficiencyDTO>> getLureEfficiency(
            @RequestParam(required = false) String fishType,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Double waterTemperature) {
        return ResponseEntity.ok(analysisService.getLureEfficiency(fishType, userId, waterTemperature));
    }
}
