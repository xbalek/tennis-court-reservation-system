package com.xbalek.tennis_court_reservation_system.repository;

import org.springframework.stereotype.Repository;

import com.xbalek.tennis_court_reservation_system.exceptions.NotFoundException;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.repository.interfaces.ReservationRepositoryInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ReservationRepository implements ReservationRepositoryInterface {
        
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Reservation findById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null || reservation.getIsDeleted()) {
            throw new NotFoundException("Reservation not found");
        }
        return reservation;
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        entityManager.persist(reservation);
        entityManager.flush();
        entityManager.refresh(reservation);
        return reservation;
    }

    @Override
    @Transactional
    public Reservation update(Reservation reservation) {
        Reservation updatedReservation = entityManager.merge(reservation);
        if (updatedReservation == null || updatedReservation.getIsDeleted()) {
            throw new NotFoundException("Reservation not found");
        }
        return updatedReservation;
    }

    @Override
    @Transactional
    public Reservation delete(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null || reservation.getIsDeleted()) {
            throw new NotFoundException("Reservation not found");
        }
        reservation.delete();
        entityManager.flush();

        return reservation;
    }
}
