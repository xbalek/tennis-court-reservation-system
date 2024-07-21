package com.xbalek.tennis_court_reservation_system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    
    private final Long id;
    
    private final String name;

    private final String phoneNumber;

    private final LocalDateTime createdAt;
}
