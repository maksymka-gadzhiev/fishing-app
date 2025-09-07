package com.example.fishtracking.service;

import com.example.fishtracking.dto.request.TackleRequest;
import com.example.fishtracking.dto.response.TackleResponse;
import com.example.fishtracking.entity.Tackle;
import com.example.fishtracking.entity.User;
import com.example.fishtracking.exception.ResourceNotFoundException;
import com.example.fishtracking.repository.TackleRepository;
import com.example.fishtracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TackleService {

    private final TackleRepository tackleRepository;
    private final UserRepository userRepository;
    public TackleResponse createTackle(TackleRequest tackleRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Tackle tackle = new Tackle();
        tackle.setName(tackleRequest.getName());
        tackle.setType(tackleRequest.getType());
        tackle.setTest(tackleRequest.getTest());
        tackle.setLength(tackleRequest.getLength());
        tackle.setHookSize(tackleRequest.getHookSize());
        tackle.setLineWeight(tackleRequest.getLineWeight());

        tackle.setUser(user);

        Tackle saveTackle = tackleRepository.save(tackle);
        return mapToResponse(saveTackle);
    }

    public List<TackleResponse> getAllTackles() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Tackle> tackles = tackleRepository.findByUser(user);
        return tackles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private TackleResponse mapToResponse(Tackle tackle) {
        TackleResponse response = new TackleResponse();
        response.setId(tackle.getId());
        response.setTest(tackle.getTest());
        response.setName(tackle.getName());
        response.setLength(tackle.getLength());
        response.setLineWeight(tackle.getLineWeight());
        response.setHookSize(tackle.getHookSize());
        response.setType(tackle.getType());
        return response;
    }

    public TackleResponse getTackle(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Tackle tackle = tackleRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException(user.getUsername() + "have not this tackle"));
        return mapToResponse(tackle);
    }

    public TackleResponse updateTackle(Long id, TackleRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Tackle tackle = tackleRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException(user.getUsername() + "have not this tackle"));
        tackle.setName(request.getName());
        tackle.setType(request.getType());
        tackle.setTest(request.getTest());
        tackle.setLength(request.getLength());
        tackle.setLineWeight(request.getLineWeight());
        tackle.setHookSize(request.getHookSize());
        Tackle updateTackle = tackleRepository.save(tackle);
        return mapToResponse(updateTackle);

    }

    public void deleteTackle(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!tackleRepository.existsByIdAndUser(id, user)) {
            throw new ResourceNotFoundException("Tackle not found");
        }
        tackleRepository.deleteById(id);
    }
}
