package com.xbalek.tennis_court_reservation_system.repository.interfaces;

import java.time.LocalDateTime;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;

public interface TennisCourtRepositoryInterface {
    TennisCourt findById(Long id);
    TennisCourt create(TennisCourt tennisCourt, Long surfaceTypeId);
    TennisCourt update(TennisCourt tennisCourt);
    TennisCourt delete(Long id);
    ReservationDTO[] getReservations(Long id);
    public void addReservation(Long courtId, Long reservationId);
    boolean isCourtReserved(Long id, LocalDateTime startTime, LocalDateTime endTime);
    public TennisCourt[] getAll();
}
