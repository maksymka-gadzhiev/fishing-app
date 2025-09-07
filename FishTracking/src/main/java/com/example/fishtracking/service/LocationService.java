package com.example.fishtracking.service;

import com.example.fishtracking.dto.request.LocationRequest;
import com.example.fishtracking.dto.response.LocationResponse;
import com.example.fishtracking.entity.Location;
import com.example.fishtracking.entity.User;
import com.example.fishtracking.exception.ResourceNotFoundException;
import com.example.fishtracking.repository.LocationRepository;
import com.example.fishtracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public LocationResponse createLocation(LocationRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Location location = new Location();
        location.setName(request.getName());
        location.setDescription(request.getDescription());
        location.setCoordinates(request.getCoordinates());
        location.setType(request.getType());
        location.setUser(user);

        Location savedLocation = locationRepository.save(location);
        return mapToResponse(savedLocation);
    }

    public List<LocationResponse> getAllLocations() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Location> locations = locationRepository.findByUser(user);
        return locations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LocationResponse getLocationById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Location location = locationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

        return mapToResponse(location);
    }

    public LocationResponse updateLocation(Long id, LocationRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Location location = locationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

        location.setName(request.getName());
        location.setDescription(request.getDescription());
        location.setCoordinates(request.getCoordinates());
        location.setType(request.getType());

        Location updatedLocation = locationRepository.save(location);
        return mapToResponse(updatedLocation);
    }

    public void deleteLocation(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Location location = locationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

        locationRepository.delete(location);
    }

    private LocationResponse mapToResponse(Location location) {
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setName(location.getName());
        response.setDescription(location.getDescription());
        response.setCoordinates(location.getCoordinates());
        response.setType(location.getType());
        return response;
    }
}