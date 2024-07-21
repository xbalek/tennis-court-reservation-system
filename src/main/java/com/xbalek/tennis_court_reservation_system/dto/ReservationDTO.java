package com.xbalek.tennis_court_reservation_system.dto;

import java.time.LocalDateTime;

import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationDTO {
    
    private final Long id;
    
    private final TennisCourt tennisCourt;
    
    private final Customer customer;
    
    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    private final Boolean isDoubles;

    private final LocalDateTime createdAt;
}
