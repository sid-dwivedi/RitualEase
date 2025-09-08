package com.example.RitualEase.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pujaName;
    private String pujaDescription;
    private double price;
}
