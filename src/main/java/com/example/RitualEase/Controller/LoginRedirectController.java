package com.example.RitualEase.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRedirectController {

    @GetMapping("/default")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PANDIT"))) {
            return "pandit_dashboard";
        } else {
            return "user_dashboard";
        }
    }
}

