package com.example.RitualEase.Repository;

import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.PanditPuja;
import com.example.RitualEase.Entity.Puja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PanditPujaRepository extends JpaRepository<PanditPuja, Long> {
    Optional<PanditPuja> findByPanditAndPuja(Pandit pandit, Puja puja);
}

