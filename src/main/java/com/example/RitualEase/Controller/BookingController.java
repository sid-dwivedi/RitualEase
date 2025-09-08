package com.example.RitualEase.Controller;
import com.example.RitualEase.Entity.Booking;
import com.example.RitualEase.Entity.User;
import com.example.RitualEase.Repository.BookingRepository;
import com.example.RitualEase.Repository.UserRepository;
import com.example.RitualEase.Service.BookingService;
import com.example.RitualEase.Service.PanditService;
import com.example.RitualEase.Service.PujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PujaService pujaService;

    @Autowired
    private PanditService panditService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/puja/{pujaId}/pandits")
    public String selectPandit(@PathVariable Long pujaId, Model model) {
        model.addAttribute("puja", pujaService.getPujaById(pujaId));
        model.addAttribute("pandits", panditService.getAllPandits());
        return "pandits"; // pandits.html
    }

    @GetMapping("/book-puja/{pujaId}/{panditId}")
    public String bookPuja(@PathVariable Long pujaId,
                           @PathVariable Long panditId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        User user=userRepository.findByUserName(userDetails.getUsername());
        bookingService.createBooking(pujaId, panditId,user);
        model.addAttribute("pujaName", pujaService.getPujaById(pujaId).getPujaName());
        model.addAttribute("panditName", panditService.getPanditById(panditId).getUserName());
        return "booking-success"; // booking-success.html

    }
    @GetMapping("/my-bookings")
    public String viewUserBooking(@AuthenticationPrincipal UserDetails userDetails,Model model){
        User user=userRepository.findByUserName(userDetails.getUsername());
        List<Booking>bookings=bookingRepository.findByUserId(user.getId());
        model.addAttribute("bookings",bookings);
        return "user-booking";
    }
}
