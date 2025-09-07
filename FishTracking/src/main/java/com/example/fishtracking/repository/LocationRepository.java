package com.example.fishtracking.repository;

import com.example.fishtracking.entity.Location;
import com.example.fishtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(User user);
    Optional<Location> findByIdAndUser(Long id, User user);
}