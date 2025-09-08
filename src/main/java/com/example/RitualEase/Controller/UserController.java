package com.example.RitualEase.Controller;

import com.example.RitualEase.Entity.Pandit;
import com.example.RitualEase.Entity.User;
import com.example.RitualEase.Repository.PanditRepository;
import com.example.RitualEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PanditRepository panditRepository;


    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/pandit-register")
    public String showPanditRegisterForm(Model model) {
        model.addAttribute("pandit", new Pandit());
        return "pandit_register";
    }

    @PostMapping("/register")
    public String registerCombinedUser(@ModelAttribute User user,
                                       @RequestParam(required = false) String expertise,
                                       Model model) {

        if (userRepository.findByUserName(user.getUserName()) != null ||
                panditRepository.findByUserName(user.getUserName()) != null) {
            model.addAttribute("error", "Username already taken.");
            return "register";
        }

        if (!user.getPassword().equals(user.getConfirm_password())) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        user.setPassword(encoder.encode(user.getPassword()));

        if ("PANDIT".equalsIgnoreCase(user.getRole())) {
            Pandit pandit = new Pandit();
            pandit.setUserName(user.getUserName());
            pandit.setEmail(user.getEmail());
            pandit.setPassword(user.getPassword());
            pandit.setConfirm_password(user.getConfirm_password());
            pandit.setExpertise(expertise);
            pandit.setRole("PANDIT");
            panditRepository.save(pandit);
        } else {
            user.setRole("USER");
            userRepository.save(user);
        }

        model.addAttribute("message", "Registered successfully.");
        return "login";
    }




    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().toString().contains("PANDIT")) {
            return "pandit_dashboard";
        } else {
            return "user_dashboard";
        }
    }
}