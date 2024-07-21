package com.xbalek.tennis_court_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
        
    private final String name;
    
    private final String phoneNumber;
}
