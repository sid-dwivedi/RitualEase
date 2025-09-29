package com.example.RitualEase.Controller;
import org.springframework.ui.Model;
import com.example.RitualEase.Entity.Booking;
import com.example.RitualEase.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookingDescription {

    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/bookings/{id}/details")
    public String panditViewBookingDetails(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        model.addAttribute("booking", booking);
        return "pandit-booking-details"; // ye ek naya page hoga
    }

    @GetMapping("/userbookings/{id}/details")
    public String userViewBookingDetails(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        model.addAttribute("booking", booking);
        return "user-booking-details"; // ye ek naya page hoga
    }


}
