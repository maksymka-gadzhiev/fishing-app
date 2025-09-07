package com.example.fishtracking.controller;

import com.example.fishtracking.dto.request.CatchRequest;
import com.example.fishtracking.dto.response.CatchResponse;
import com.example.fishtracking.dto.response.CatchStatsResponse;
import com.example.fishtracking.service.CatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catch")
public class CatchController {
    private final CatchService catchService;

    @PostMapping
    public ResponseEntity<CatchResponse> createCatch(@RequestBody CatchRequest request) {
        return ResponseEntity.ok(catchService.createCatch(request));
    }

    @GetMapping
    public ResponseEntity<Page<CatchResponse>> getAllCatches(Pageable pageable) {
        return ResponseEntity.ok(catchService.getAllCatches(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatchResponse> getCatchById(@PathVariable Long id) {
        return ResponseEntity.ok(catchService.getCatchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatchResponse> updateCatch(@PathVariable Long id, @RequestBody CatchRequest request) {
        return ResponseEntity.ok(catchService.updateCatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatch(@PathVariable Long id) {
        catchService.deleteCatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/summary")
    public ResponseEntity<CatchStatsResponse> getCatchStatsSummary() {
        return ResponseEntity.ok(catchService.getCatchStatsSummary());
    }

    @GetMapping("/stats/by-fish-type")
    public ResponseEntity<Map<String, Long>> getCatchStatsByFishType() {
        return ResponseEntity.ok(catchService.getCatchStatsByFishType());
    }

    @GetMapping("/stats/by-location")
    public ResponseEntity<Map<String, Long>> getCatchStatsByLocation() {
        return ResponseEntity.ok(catchService.getCatchStatsByLocation());
    }
}