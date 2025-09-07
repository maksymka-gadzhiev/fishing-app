package com.example.fishtracking.service;

import com.example.fishtracking.dto.request.LureRequest;
import com.example.fishtracking.dto.response.LureResponse;
import com.example.fishtracking.entity.Lure;
import com.example.fishtracking.entity.User;
import com.example.fishtracking.exception.ResourceNotFoundException;
import com.example.fishtracking.repository.LureRepository;
import com.example.fishtracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LureService {
    private final UserRepository userRepository;
    private final LureRepository lureRepository;
    public LureResponse createLure(LureRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Lure lure = new Lure();
        lure.setName(request.getName());
        lure.setType(request.getType());
        lure.setColor(request.getColor());
        lure.setSize(request.getSize());
        lure.setWeight(request.getWeight());
        lure.setUser(user);

        Lure savedLure = lureRepository.save(lure);

        return mapToResponse(savedLure);
    }

    public List<LureResponse> getAllLures() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Lure> lures = lureRepository.findByUser(user);
        return lures.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public LureResponse getLure(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Lure lure = lureRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Lure not found"));
        return mapToResponse(lure);
    }

    public LureResponse updateLure(Long id, LureRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Lure lure = lureRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Lure not found"));

        lure.setName(request.getName());
        lure.setType(request.getType());
        lure.setColor(request.getColor());
        lure.setSize(request.getSize());
        lure.setWeight(request.getWeight());

        Lure updateLure = lureRepository.save(lure);
        return mapToResponse(updateLure);
    }

    public void deleteLure(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Lure lure = lureRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Lure not found"));

        lureRepository.delete(lure);
    }

    private LureResponse mapToResponse(Lure lure) {
        LureResponse lureResponse = new LureResponse();
        lureResponse.setId(lure.getId());
        lureResponse.setColor(lure.getColor());
        lureResponse.setSize(lure.getSize());
        lureResponse.setWeight(lure.getWeight());
        lureResponse.setName(lure.getName());
        lureResponse.setType(lure.getType());
        return lureResponse;
    }
}
