package com.example.RitualEase.Controller;

import com.example.RitualEase.Entity.*;
import com.example.RitualEase.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
    @Autowired
    private PujaRepository pujaRepository;
    @Autowired
    private PanditPujaRepository panditPujaRepository;
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

    @PostMapping("/addpuja/{pujaId}")
    public String addPujaToPandit(@PathVariable Long pujaId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam String location) {
        Pandit pandit = panditRepository.findByUserName(userDetails.getUsername());
        Puja puja = pujaRepository.findById(pujaId).orElse(null);

        if (pandit != null && puja != null) {
            PanditPuja pp = new PanditPuja();
            pp.setPandit(pandit);
            pp.setPuja(puja);
            pp.setLocation(location);

            // ✅ ab isko save karo
            panditPujaRepository.save(pp);
        }
        return "pandit_dashboard";
    }

//    @PostMapping("/addpuja/{pujaId}")
//    public String addPujaToPandit(@PathVariable Long pujaId,
//                                  @AuthenticationPrincipal UserDetails userDetails,
//                                  @RequestParam String location) {
//
//        Pandit pandit = panditRepository.findByUserName(userDetails.getUsername());
//        Puja puja = pujaRepository.findById(pujaId).orElse(null);
//
//        if (pandit != null) {
//            pandit.setLocation(location);   // ✅ सिर्फ address save होगा
//            panditRepository.save(pandit);
//        }
//
//        if (pandit != null && puja != null) {
//            if (pandit.getPujas() == null) {
//                pandit.setPujas(new ArrayList<>());
//            }
//            if (!pandit.getPujas().contains(puja)) {
//                pandit.getPujas().add(puja);
//                panditRepository.save(pandit);
//            }
//        }
//        return "pandit_dashboard";
//    }
}

