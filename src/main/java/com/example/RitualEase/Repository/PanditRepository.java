package com.example.RitualEase.Repository;
import com.example.RitualEase.Entity.Pandit;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PanditRepository extends JpaRepository<Pandit,Long> {
   Pandit findByUserName(String userName);
}
