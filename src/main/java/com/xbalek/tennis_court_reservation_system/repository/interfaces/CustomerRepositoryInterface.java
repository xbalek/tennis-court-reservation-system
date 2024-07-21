package com.xbalek.tennis_court_reservation_system.repository.interfaces;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.CustomerRole;

public interface CustomerRepositoryInterface {
    public Customer findById(Long id);
    public CustomerRole getRole(Long id);
    public Customer findByPhoneNumber(String phoneNumber);
    public Customer create(Customer customer);
    public Customer delete(Long id);
    public ReservationDTO[] getReservation(String phoneNumber, boolean onlyFutureReservations);
    public void addReservation(Long customerId, Long reservationId);
}
