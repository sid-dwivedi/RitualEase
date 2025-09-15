    package com.example.RitualEase.Entity;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.List;
    @Data
    @Entity
    @Table(name = "pandits")
    public class Pandit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userName;
        private String email;
        private String password;
        private String confirm_password;

        private String expertise;
        private String location;
        private Double lat;
        private Double lon;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
        // Optional:

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }

        public String getExpertise() {
            return expertise;
        }

        public void setExpertise(String expertise) {
            this.expertise = expertise;
        }
        private String role = "PANDIT"; // default
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        @ManyToMany
        @JoinTable(
                name = "pandit_puja",
                joinColumns = @JoinColumn(name = "pandit_id"),
                inverseJoinColumns = @JoinColumn(name = "puja_id")
        )
        private List<Puja> pujas;
        public List<Puja> getPujas() {
            return pujas;
        }

        public void setPujas(List<Puja> pujas) {
            this.pujas = pujas;
        }

    }
