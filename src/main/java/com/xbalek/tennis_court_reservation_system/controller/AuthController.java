package com.xbalek.tennis_court_reservation_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.LoginRequest;
import com.xbalek.tennis_court_reservation_system.security.JWTProvider;
import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private CustomerServiceInterface customerService;

    @Autowired
    private JWTProvider tokenProvider;

    @PostMapping("/")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getPhoneNumber() == null || loginRequest.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        CustomerDTO customer = customerService.findByPhoneNumber(loginRequest.getPhoneNumber());
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        if (!customer.getName().equals(loginRequest.getName())) {
            return ResponseEntity.status(401).build();
        }
        String token = tokenProvider.generateToken(customer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).body("Login successful");
    }
}
