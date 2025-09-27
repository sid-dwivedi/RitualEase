package com.example.RitualEase.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pandit_puja")
public class PanditPuja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // pandit_id foreign key
    @ManyToOne
    @JoinColumn(name = "pandit_id")
    private Pandit pandit;

    // puja_id foreign key
    @ManyToOne
    @JoinColumn(name = "puja_id")
    private Puja puja;

    private String location;
    private double latitude;
    private double longitude;

    // getters, setters
}

