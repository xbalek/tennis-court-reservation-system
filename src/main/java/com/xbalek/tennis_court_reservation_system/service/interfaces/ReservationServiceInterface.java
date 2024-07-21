package com.xbalek.tennis_court_reservation_system.service.interfaces;

import java.math.BigDecimal;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Reservation;

public interface ReservationServiceInterface {
    
    ReservationDTO findById(Long id);
    BigDecimal create(Reservation reservation);
    ReservationDTO update(ReservationDTO reservationDetails);
    ReservationDTO delete(Long id);
}
