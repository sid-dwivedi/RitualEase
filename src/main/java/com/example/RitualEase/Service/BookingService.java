package com.example.RitualEase.Service;
import com.example.RitualEase.Entity.*;
import com.example.RitualEase.Repository.BookingRepository;
import com.example.RitualEase.Repository.PujaRepository;
import com.example.RitualEase.Repository.PanditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PujaRepository pujaRepository;

    @Autowired
    private PanditRepository panditRepository;

    public Booking createBooking(Long pujaId, Long panditId,User user) {
        Puja puja = pujaRepository.findById(pujaId).orElseThrow();
        Pandit pandit = panditRepository.findById(panditId).orElseThrow();
        Booking booking = new Booking();
        booking.setPuja(puja);
        booking.setPandit(pandit);
        booking.setUser(user);
        booking.setDate(LocalDate.now());
        booking.setTimeSlot("Morning");
        booking.setStatus(BookingStatus.valueOf("PENDING"));
        return bookingRepository.save(booking);
    }
}
