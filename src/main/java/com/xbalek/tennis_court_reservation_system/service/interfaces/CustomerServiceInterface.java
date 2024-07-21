package com.xbalek.tennis_court_reservation_system.service.interfaces;

import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Customer;

public interface CustomerServiceInterface {
    
    public CustomerDTO findById(Long id);
    public boolean isAdmin(Long id);
    public CustomerDTO findByPhoneNumber(String phoneNumber);
    public CustomerDTO create(Customer customer);
    public CustomerDTO delete(Long id);
    public ReservationDTO[] getReservation(String phoneNumber, boolean onlyFutureReservations);
}
