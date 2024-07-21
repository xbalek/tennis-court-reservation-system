package com.xbalek.tennis_court_reservation_system.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.exceptions.NotFoundException;
import com.xbalek.tennis_court_reservation_system.mappers.ReservationMapper;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.CustomerRole;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.repository.interfaces.CustomerRepositoryInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class CustomerRepository implements CustomerRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    @Transactional
    public Customer findById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.getIsDeleted()) {
            return null;
        }
        return customer;
    }

    @Override
    @Transactional
    public CustomerRole getRole(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.getIsDeleted()) {
            return null;
        }
        return customer.getRole();
    }

    @Override
    @Transactional
    public Customer findByPhoneNumber(String phoneNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customer = cq.from(Customer.class);

        Predicate phoneNumberPredicate = cb.equal(customer.get("phoneNumber"), phoneNumber);
        Predicate isDeletedPredicate = cb.isFalse(customer.get("isDeleted"));
  
        cq.where(cb.and(phoneNumberPredicate, isDeletedPredicate));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        entityManager.persist(customer);
        entityManager.flush();
        entityManager.refresh(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer delete(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.getIsDeleted()) {
            return null;
        }
        customer.delete();
        entityManager.flush();

        return customer;
    }

    @Override
    @Transactional
    public ReservationDTO[] getReservation(String phoneNumber, boolean onlyFutureReservations) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> reservation = cq.from(Reservation.class);

        Predicate customerIdPredicate = cb.equal(reservation.get("customer").get("phoneNumber"), phoneNumber);
        Predicate isDeletedPredicate = cb.isFalse(reservation.get("isDeleted"));
        if (onlyFutureReservations) {
            Predicate futureReservationsPredicate = cb.greaterThan(reservation.get("startTime"), cb.currentTimestamp());
            cq.where(cb.and(customerIdPredicate, isDeletedPredicate, futureReservationsPredicate));
        } else {
            cq.where(cb.and(customerIdPredicate, isDeletedPredicate));
        }
        cq.orderBy(cb.asc(reservation.get("createdAt")));
        List<Reservation> reservations = entityManager.createQuery(cq).getResultList();
        List<ReservationDTO> resultList = reservationMapper.toDTOList(reservations);

        return resultList.toArray(ReservationDTO[]::new);
    }

    @Override
    @Transactional
    public void addReservation(Long customerId, Long reservationId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        Reservation reservation = entityManager.find(Reservation.class, reservationId);
        if (customer == null || customer.getIsDeleted()) {
            throw new NotFoundException("Customer not found");
        }
        if (reservation == null || reservation.getIsDeleted()) {
            throw new NotFoundException("Reservation not found");
        }
        customer.addReservation(reservation);
        entityManager.flush();
    }
}
