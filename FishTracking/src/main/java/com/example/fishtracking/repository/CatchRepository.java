package com.example.fishtracking.repository;

import com.example.fishtracking.entity.Catch;
import com.example.fishtracking.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatchRepository extends JpaRepository<Catch, Long> {
    Page<Catch> findByUser(User user, Pageable pageable);
    Optional<Catch> findByIdAndUser(Long id, User user);

    @Query("SELECT COUNT(c) FROM Catch c WHERE c.user = :user")
    Long countByUser(@Param("user") User user);

    @Query("SELECT SUM(c.weight) FROM Catch c WHERE c.user = :user")
    Double sumWeightByUser(@Param("user") User user);

    @Query("SELECT c.fishType, COUNT(c) FROM Catch c WHERE c.user = :user GROUP BY c.fishType")
    List<Object[]> countByFishType(@Param("user") User user);

    @Query("SELECT l.name, COUNT(c) FROM Catch c JOIN c.location l WHERE c.user = :user GROUP BY l.name")
    List<Object[]> countByLocation(@Param("user") User user);
}