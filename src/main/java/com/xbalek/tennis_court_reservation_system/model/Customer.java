package com.xbalek.tennis_court_reservation_system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;
    
    @Column(unique = true)
    private String phoneNumber;
    private String name;
    @Setter private CustomerRole role;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.reservations = new ArrayList<>();
        this.role = CustomerRole.USER;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
