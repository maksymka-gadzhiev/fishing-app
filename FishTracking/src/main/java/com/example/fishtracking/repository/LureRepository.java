package com.example.fishtracking.repository;

import com.example.fishtracking.entity.Lure;
import com.example.fishtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LureRepository extends JpaRepository<Lure, Long> {
    List<Lure> findByUser(User user);
    Optional<Lure> findByIdAndUser(Long id, User user);
}
