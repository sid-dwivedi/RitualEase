package com.example.RitualEase.Controller;

import com.example.RitualEase.Entity.Booking;
import com.example.RitualEase.Entity.BookingStatus;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Repository.BookingRepository;
import com.example.RitualEase.Repository.PanditRepository;
import com.example.RitualEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pandit")
public class PanditBookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PanditRepository panditRepository;
    @GetMapping("/booking")
    public String viewPanditBookings(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Pandit pandit = panditRepository.findByUserName(userDetails.getUsername());
        List<Booking> bookings = bookingRepository.findByPanditId(pandit.getId());
        model.addAttribute("bookings", bookings);
        return "pandit-booking";
    }

    @GetMapping("/bookings/{id}/accept")
    public String acceptBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.ACCEPTED);
        bookingRepository.save(booking);
        return "redirect:/pandit/booking";
    }

    @GetMapping("/bookings/{id}/reject")
    public String rejectBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return "redirect:/pandit/booking";
    }
    }

