package com.xbalek.tennis_court_reservation_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.exceptions.NotFoundException;
import com.xbalek.tennis_court_reservation_system.mappers.ReservationMapper;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.repository.interfaces.TennisCourtRepositoryInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class TennisCourtRepository implements TennisCourtRepositoryInterface {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public TennisCourt findById(Long id) {
        TennisCourt foundCourt = entityManager.find(TennisCourt.class, id);
        if (foundCourt == null || foundCourt.getIsDeleted()) {
            throw new NotFoundException("Tennis court not found");
        }
        return foundCourt;
    }

    @Override
    @Transactional
    public TennisCourt create(TennisCourt tennisCourt, Long surfaceTypeId) {
        SurfaceType courtSurface = entityManager.find(SurfaceType.class, surfaceTypeId);
        if (courtSurface == null || courtSurface.getIsDeleted()) {
            throw new NotFoundException("Surface type not found");
        }
        tennisCourt.setSurfaceType(courtSurface);
        entityManager.persist(tennisCourt);
        entityManager.flush();
        entityManager.refresh(tennisCourt);
        return tennisCourt;
    }

    @Override
    @Transactional
    public TennisCourt update(TennisCourt tennisCourt) {
        TennisCourt updatedCourt = entityManager.merge(tennisCourt);
        if (updatedCourt == null || updatedCourt.getIsDeleted()) {
            throw new NotFoundException("Tennis court not found");
        }
        return updatedCourt;
    }

    @Override
    @Transactional
    public TennisCourt delete(Long id) {
        TennisCourt foundCourt = entityManager.find(TennisCourt.class, id);
        if (foundCourt == null || foundCourt.getIsDeleted()) {
            throw new NotFoundException("Tennis court not found");
        }
        foundCourt.delete();
        entityManager.flush();

        return foundCourt;
    }

    @Override
    @Transactional
    public boolean isCourtReserved(Long id, LocalDateTime startTime, LocalDateTime endTime) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservation> reservation = cq.from(Reservation.class);

        Predicate courtIdPredicate = cb.equal(reservation.get("tennisCourt").get("id"), id);
        Predicate startTimePredicate = cb.lessThan(reservation.get("startTime"), endTime);
        Predicate endTimePredicate = cb.greaterThan(reservation.get("endTime"), startTime);
        Predicate isDeletedPredicate = cb.isFalse(reservation.get("isDeleted"));
        
        cq.select(cb.count(reservation))
          .where(cb.and(courtIdPredicate, startTimePredicate, endTimePredicate, isDeletedPredicate));
        Long count = entityManager.createQuery(cq).getSingleResult();

        return count > 0;
    }

    @Override
    @Transactional
    public ReservationDTO[] getReservations(Long id) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> reservation = cq.from(Reservation.class);

        Predicate courtIdPredicate = cb.equal(reservation.get("tennisCourt").get("id"), id);
        Predicate isDeletedPredicate = cb.isFalse(reservation.get("isDeleted"));

        cq.where(cb.and(courtIdPredicate, isDeletedPredicate));
        cq.orderBy(cb.asc(reservation.get("createdAt")));
        List<Reservation> reservations = entityManager.createQuery(cq).getResultList();
        List<ReservationDTO> resultList = reservationMapper.toDTOList(reservations);

        return resultList.toArray(ReservationDTO[]::new);
    }

    @Override
    @Transactional
    public void addReservation(Long courtId, Long reservationId) {
        TennisCourt court = entityManager.find(TennisCourt.class, courtId);
        Reservation reservation = entityManager.find(Reservation.class, reservationId);
        if (court == null || court.getIsDeleted()) {
            throw new NotFoundException("Tennis court not found");
        }
        if (reservation == null || reservation.getIsDeleted()) {
            throw new NotFoundException("Reservation not found");
        }
        court.addReservation(reservation);
        entityManager.flush();
    }

    @Override
    @Transactional
    public TennisCourt[] getAll() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TennisCourt> cq = cb.createQuery(TennisCourt.class);
        Root<TennisCourt> courtRoot = cq.from(TennisCourt.class);

        Predicate isDeletedPredicate = cb.isFalse(courtRoot.get("isDeleted"));
        cq.where(isDeletedPredicate);

        List<TennisCourt> resultList = entityManager.createQuery(cq).getResultList();

        return resultList.toArray(TennisCourt[]::new);
    }
}
