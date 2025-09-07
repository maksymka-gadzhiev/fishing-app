package com.example.fishtracking.controller;

import com.example.fishtracking.dto.request.LureRequest;
import com.example.fishtracking.dto.response.LureResponse;
import com.example.fishtracking.service.LureService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lure")
public class LureController {
    private final LureService lureService;

    @PostMapping
    public ResponseEntity<LureResponse> createLure(@RequestBody LureRequest request) {
        return ResponseEntity.ok(lureService.createLure(request));
    }

    @GetMapping
    public ResponseEntity<List<LureResponse>> getAllLures() {
        return ResponseEntity.ok(lureService.getAllLures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LureResponse> getLure(@PathVariable Long id) {
        return ResponseEntity.ok(lureService.getLure(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LureResponse> updateLure(@PathVariable Long id, @RequestBody LureRequest request) {
        return ResponseEntity.ok(lureService.updateLure(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLure(@PathVariable Long id) {
        lureService.deleteLure(id);
        return ResponseEntity.noContent().build();
    }
}
