package com.xbalek.tennis_court_reservation_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerServiceInterface customerService;

    @Autowired
    private JWTHeaderValidator jwtHeaderValidator;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            if (!jwtHeaderValidator.validateRequest(request, id)) {
                return ResponseEntity.status(401).build();
            }
        }
        CustomerDTO customer = customerService.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<CustomerDTO> findByPhoneNumber(@PathVariable String phoneNumber,  HttpServletRequest request) {
        CustomerDTO customer = customerService.findByPhoneNumber(phoneNumber);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        if (jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.ok(customer);
        }
        if (!jwtHeaderValidator.validateRequest(request, customer.getId())) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{phoneNumber}/reservations")
    public ResponseEntity<ReservationDTO[]> getReservation(@PathVariable String phoneNumber,
                    @RequestParam(name = "onlyFutureReservations", defaultValue = "false") boolean onlyFutureReservations,
                    HttpServletRequest request) {
        if (jwtHeaderValidator.isAdminFromRequest(request)) {
            ReservationDTO[] reservations = customerService.getReservation(phoneNumber, onlyFutureReservations);
            if (reservations == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(reservations);
        }
        CustomerDTO customer = customerService.findByPhoneNumber(phoneNumber);
        if (!jwtHeaderValidator.validateRequest(request, customer.getId())) {
            return ResponseEntity.status(401).build();
        }
        ReservationDTO[] reservations = customerService.getReservation(phoneNumber, onlyFutureReservations);
        if (reservations == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

}
