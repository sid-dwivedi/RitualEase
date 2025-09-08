package com.example.RitualEase.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pandit_id")
    private Pandit pandit;
    @ManyToOne
    @JoinColumn(name = "puja_id")
    private Puja puja;
    private LocalDate date;
    private String timeSlot;
    @Enumerated(EnumType.STRING)
    private BookingStatus status=BookingStatus.PENDING;
}
