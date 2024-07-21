package com.xbalek.tennis_court_reservation_system.service.interfaces;

import java.time.LocalDateTime;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.dto.TennisCourtDTO;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;

public interface TennisCourtServiceInterface {
    TennisCourtDTO findById(Long id);
    TennisCourtDTO create(TennisCourt tennisCourt, Long surfaceTypeId);
    TennisCourtDTO update(Long id, TennisCourtDTO TenniscourtDetails);
    TennisCourtDTO delete(Long id);
    ReservationDTO[] getReservations(Long id);
    boolean isCourtReserved(Long id, LocalDateTime startTime, LocalDateTime endTime);
    public TennisCourtDTO[] getAll();
}
