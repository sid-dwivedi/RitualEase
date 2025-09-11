package com.example.RitualEase.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String confirm_password;

    private String location;
    private Double lat;
    private Double lon;

    private String TimeSlot;
    public String getTimeslot() {
        return TimeSlot;
    }

    public void setTimeslot(String timeslot) {
        this.TimeSlot = timeslot;
    }
    private String role = "USER";
}

