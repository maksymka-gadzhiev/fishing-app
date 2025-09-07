package com.example.fishtracking.controller;

import com.example.fishtracking.dto.request.TackleRequest;
import com.example.fishtracking.dto.response.TackleResponse;
import com.example.fishtracking.service.TackleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tackle")
public class TackleController {
    private final TackleService tackleService;

    @PostMapping
    public ResponseEntity<TackleResponse> createTackle(@RequestBody TackleRequest tackleRequest) {
        return ResponseEntity.ok(tackleService.createTackle(tackleRequest));
    }

    @GetMapping
    public ResponseEntity<List<TackleResponse>> getAllTackles() {
        return ResponseEntity.ok(tackleService.getAllTackles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TackleResponse> getTackle(@PathVariable Long id) {
        return ResponseEntity.ok(tackleService.getTackle(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TackleResponse> updateTackle(@PathVariable Long id,
                                                    @RequestBody TackleRequest request) {
        return ResponseEntity.ok(tackleService.updateTackle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTackle(@PathVariable Long id) {
        tackleService.deleteTackle(id);
        return ResponseEntity.noContent().build();
    }

}
