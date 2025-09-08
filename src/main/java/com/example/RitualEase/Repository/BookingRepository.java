package com.example.RitualEase.Repository;

import com.example.RitualEase.Entity.Booking;
import com.example.RitualEase.Entity.BookingStatus;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository <Booking,Long>{
    List<Booking>findByUser(User user);
    List<Booking>findByUserId(Long userId);
    List<Booking>findByPandit(Pandit pandit);
    List<Booking>findByPanditId(Long panditId);
}
