package com.example.RitualEase.Repository;

import com.example.RitualEase.Entity.PanditPuja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanditPujaRepository extends JpaRepository<PanditPuja, Long> {
}

