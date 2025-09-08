package com.example.RitualEase.Sequrity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // PasswordEncoder Bean (fix for your error)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers("/", "/register", "/css/**", "/js/**").permitAll()
                        // Only USER can access these
                        .requestMatchers("/my-bookings/**", "/book-puja/**", "/puja/**").hasRole("USER")
                        // Only PANDIT (admin) can access these
                        .requestMatchers("/pandit/**", "/admin/**").hasRole("PANDIT")
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                // Login configuration
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true) // after login redirect
                        .permitAll()
                )
                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
