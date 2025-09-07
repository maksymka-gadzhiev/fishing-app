package com.example.fishtracking.repository;

import com.example.fishtracking.entity.Tackle;
import com.example.fishtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TackleRepository extends JpaRepository<Tackle, Long> {
    List<Tackle> findByUser(User user);
    Optional<Tackle> findByIdAndUser(Long id, User user);

    boolean existsByIdAndUser(Long id, User user);
}
