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

    /**
     * Create a booking with puja, pandit, user, and timeslot.
     * Location is fetched from the user entity (inline selection).
     */
    public Booking createBooking(Long pujaId, Long panditId, User user, String timeSlot,LocalDate pujaDate) {
        Puja puja = pujaRepository.findById(pujaId).orElseThrow();
        Pandit pandit = panditRepository.findById(panditId).orElseThrow();

        Booking booking = new Booking();
        booking.setPuja(puja);
        booking.setPandit(pandit);
        booking.setUser(user);
        booking.setDate(LocalDate.now());
        booking.setPujaDate(pujaDate);
        booking.setTimeSlot(timeSlot);
        booking.setStatus(BookingStatus.PENDING);

        // Save user location in booking
        booking.setLocation(user.getLocation());
//        booking.setLat(user.getLat());
//        booking.setLon(user.getLon());

        return bookingRepository.save(booking);
    }

}
