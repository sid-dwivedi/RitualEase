package com.example.RitualEase.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pujaName;
    private String pujaDescription;
    private double price;
    @ManyToMany(mappedBy = "pujas")
    private List<Pandit> pandits;
}
