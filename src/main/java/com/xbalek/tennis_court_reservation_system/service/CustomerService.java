package com.xbalek.tennis_court_reservation_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.mappers.CustomerMapper;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.CustomerRole;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.repository.CustomerRepository;
import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

@Service
public class CustomerService implements CustomerServiceInterface {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerDTO findById(Long id) {
        return customerMapper.toDTO(customerRepository.findById(id));
    }

    @Override
    public boolean isAdmin(Long id) {
        return customerRepository.getRole(id) == CustomerRole.ADMIN;
    }
    
    @Override
    public CustomerDTO findByPhoneNumber(String phoneNumber) {
        return customerMapper.toDTO(customerRepository.findByPhoneNumber(phoneNumber));
    }
    
    @Override
    public CustomerDTO create(Customer customer) {
        return customerMapper.toDTO(customerRepository.create(customer));
    }
    
    @Override
    public CustomerDTO delete(Long id) {
        Customer customer = customerRepository.delete(id);
        for (Reservation reservation : customer.getReservations()) {
            customerRepository.delete(reservation.getId());
        }
        return customerMapper.toDTO(customer);
    }
    
    @Override
    public ReservationDTO[] getReservation(String phoneNumber, boolean onlyFutureReservations) {
        return customerRepository.getReservation(phoneNumber, onlyFutureReservations);
    }
}
