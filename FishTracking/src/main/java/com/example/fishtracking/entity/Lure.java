package com.example.fishtracking.entity;

import com.example.fishtracking.enums.LureType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="lures")
public class Lure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private LureType type;

    private String color;
    private Double size;
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "lure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Catch> catches;

}
