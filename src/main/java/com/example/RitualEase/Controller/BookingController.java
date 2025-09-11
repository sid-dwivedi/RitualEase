package com.example.RitualEase.Controller;

import com.example.RitualEase.Entity.Booking;
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

    // 1️⃣ Show pandits for a puja with inline location & timeslot
    @GetMapping("/puja/{pujaId}/pandits")
    public String selectPandit(@PathVariable Long pujaId, Model model) {
        model.addAttribute("puja", pujaService.getPujaById(pujaId));
        model.addAttribute("pandits", panditService.getAllPandits());
        return "pandits"; // pandits.html (inline form handles location + timeslot)
    }

    // 2️⃣ Book puja with selected pandit, location, and timeslot
    @GetMapping("/book-puja/{pujaId}/{panditId}")
    public String bookPuja(@PathVariable Long pujaId,
                           @PathVariable Long panditId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String location,
//                           @RequestParam Double lat,
//                           @RequestParam Double lon,
                           @RequestParam("pujaDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pujaDate,
                           @RequestParam String timeslot,
                           Model model) {

        User user = userRepository.findByUserName(userDetails.getUsername());

        // Update user with location + timeslot
        user.setLocation(location);
//        user.setLat(lat);
//        user.setLon(lon);
        user.setTimeslot(timeslot);
        userRepository.save(user);

        // Create booking
        bookingService.createBooking(pujaId, panditId, user, timeslot,pujaDate);

        model.addAttribute("pujaName", pujaService.getPujaById(pujaId).getPujaName());
        model.addAttribute("panditName", panditService.getPanditById(panditId).getUserName());

        return "booking-success";
    }

    // 3️⃣ Save user location from inline form
    @PostMapping("/location/save")
    public String saveLocation(@RequestParam String displayName,
                               @RequestParam Double lat,
                               @RequestParam Double lon,
                               @AuthenticationPrincipal UserDetails userDetails,
                               HttpSession session) {
        User user = userRepository.findByUserName(userDetails.getUsername());
        user.setLocation(displayName);
        user.setLat(lat);
        user.setLon(lon);
        userRepository.save(user);

        // Continue booking if pending puja exists
        Long pujaId = (Long) session.getAttribute("pendingPujaId");
        Long panditId = (Long) session.getAttribute("pendingPanditId");
        if(pujaId != null && panditId != null) {
            return "redirect:/book-puja/" + pujaId + "/" + panditId;
        }

        return "redirect:/dashboard"; // default page
    }

    // 4️⃣ Save timeslot from inline form
    @PostMapping("/timeslot/save")
    public String saveTimeSlot(@RequestParam String slot, HttpSession session) {
        session.setAttribute("timeslot", slot);

        Long pujaId = (Long) session.getAttribute("pendingPujaId");
        Long panditId = (Long) session.getAttribute("pendingPanditId");
        if(pujaId != null && panditId != null) {
            return "redirect:/book-puja/" + pujaId + "/" + panditId;
        }

        return "redirect:/dashboard";
    }

    // 5️⃣ User bookings page
    @GetMapping("/my-bookings")
    public String viewUserBooking(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUserName(userDetails.getUsername());
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        return "user-booking"; // user-booking.html
    }
}
