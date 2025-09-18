package com.example.RitualEase.Repository;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.PanditPuja;
import com.example.RitualEase.Entity.Puja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanditRepository extends JpaRepository<Pandit,Long> {
   Pandit findByUserName(String userName);
   List<Pandit> findByPujas_Id(Long pujaId);
}
