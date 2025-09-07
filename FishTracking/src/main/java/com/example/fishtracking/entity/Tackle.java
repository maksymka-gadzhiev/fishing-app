package com.example.fishtracking.entity;

import com.example.fishtracking.enums.TackleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "tackles")
@NoArgsConstructor
public class Tackle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private TackleType type;

    private Double length;
    private Double test;

    @Column(name = "line_weight")
    private Double lineWeight;

    @Column(name = "hook_size")
    private Double hookSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tackle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Catch> catches;


}
