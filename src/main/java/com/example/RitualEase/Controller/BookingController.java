package com.example.RitualEase.Controller;

import com.example.RitualEase.Entity.Booking;
import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.User;
import com.example.RitualEase.Repository.BookingRepository;
import com.example.RitualEase.Repository.UserRepository;
import com.example.RitualEase.Service.BookingService;
import com.example.RitualEase.Service.PanditService;
import com.example.RitualEase.Service.PujaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {

    @Autowired private UserRepository userRepository;
    @Autowired private BookingService bookingService;
    @Autowired private PujaService pujaService;
    @Autowired private PanditService panditService;
    @Autowired private BookingRepository bookingRepository;

    // ✅ Add user to every model
    @ModelAttribute("user")
    public User addUserToModel(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null) {
            return userRepository.findByUserName(userDetails.getUsername());
        }
        return null;
    }

    @GetMapping("/puja/{pujaId}/pandits")
    public String selectPandit(@PathVariable Long pujaId,
                               @RequestParam double lat,
                               @RequestParam double lon,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pujaDate,
                               @RequestParam String timeslot,
                               Model model) {
        model.addAttribute("puja", pujaService.getPujaById(pujaId));
        List<Pandit> pandits = panditService.getPanditsByPujaAndLocation(pujaId, lat, lon, 50);
        model.addAttribute("pandits", pandits);

        model.addAttribute("selectedLat", lat);
        model.addAttribute("selectedLon", lon);
        model.addAttribute("selectedDate", pujaDate);
        model.addAttribute("selectedTimeslot", timeslot);

        return "pandits"; // pandits.html
    }


    @GetMapping("/book-puja/{pujaId}/{panditId}")
    public String bookPuja(@PathVariable Long pujaId,
                           @PathVariable Long panditId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String location,
                           @RequestParam("pujaDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pujaDate,
                           @RequestParam String timeslot,
                           Model model) {

        User user = userRepository.findByUserName(userDetails.getUsername());

        //  Update location if changed
        if(location != null && !location.isEmpty()) {
            user.setLocation(location);
        }

        user.setTimeslot(timeslot);
        userRepository.save(user);

        bookingService.createBooking(pujaId, panditId, user, timeslot, pujaDate);

        model.addAttribute("pujaName", pujaService.getPujaById(pujaId).getPujaName());
        model.addAttribute("panditName", panditService.getPanditById(panditId).getUserName());
        model.addAttribute("location", user.getLocation());

        return "booking-success";
    }

    @GetMapping("/dashboard/location")
    public String showLocationForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUserName(userDetails.getUsername());
        model.addAttribute("location", user.getLocation());
        return "fragments/locationForm :: locationForm";
    }

    @PostMapping("/dashboard/location/save")
    public String saveDashboardLocation(@RequestParam String location,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername());
        user.setLocation(location);
        userRepository.save(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/my-bookings")
    public String viewUserBooking(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUserName(userDetails.getUsername());
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        return "user-booking";
    }
}
