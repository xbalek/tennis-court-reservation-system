package com.xbalek.tennis_court_reservation_system.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.interfaces.ReservationServiceInterface;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationServiceInterface reservationService;

    @Autowired
    private JWTHeaderValidator jwtHeaderValidator;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable Long id, HttpServletRequest request) {
        ReservationDTO reservation = reservationService.findById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            if (!jwtHeaderValidator.validateRequest(request, reservation.getCustomer().getId())) {
                return ResponseEntity.status(401).build();
            }
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<BigDecimal> create(@RequestBody Reservation reservation, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            if (!jwtHeaderValidator.validateRequest(request, reservation.getCustomer().getId())) {
                return ResponseEntity.status(401).build();
            }
        }
        BigDecimal price;
        try {
            price = reservationService.create(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().header("Error", e.getMessage()).build();
        }
        return ResponseEntity.ok(price);
    }

    @PutMapping("/")
    public ResponseEntity<ReservationDTO> update(@RequestBody ReservationDTO reservationDetails, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        ReservationDTO reservation = reservationService.update(reservationDetails);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDTO> delete(@PathVariable Long id, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        ReservationDTO reservation = reservationService.delete(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

}
