package com.xbalek.tennis_court_reservation_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "court_id")
    @Setter private TennisCourt tennisCourt;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Setter private Customer customer;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isDoubles;
    private LocalDateTime createdAt;
    private Boolean isDeleted;

    public Reservation(TennisCourt tennisCourt, Customer customer, LocalDateTime startTime, LocalDateTime endTime, Boolean isDoubles) {
        this.tennisCourt = tennisCourt;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDoubles = isDoubles;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public int getDuration() {
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }
}
