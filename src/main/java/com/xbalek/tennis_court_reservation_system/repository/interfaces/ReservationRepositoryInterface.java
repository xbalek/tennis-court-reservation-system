package com.xbalek.tennis_court_reservation_system.repository.interfaces;

import com.xbalek.tennis_court_reservation_system.model.Reservation;

public interface ReservationRepositoryInterface {
    public Reservation findById(Long id);
    public Reservation create(Reservation reservation);
    public Reservation update(Reservation reservation);
    public Reservation delete(Long id);
}
