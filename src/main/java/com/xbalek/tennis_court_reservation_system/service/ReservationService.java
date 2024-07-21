package com.xbalek.tennis_court_reservation_system.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.exceptions.AlreadyReservedException;
import com.xbalek.tennis_court_reservation_system.exceptions.NotFoundException;
import com.xbalek.tennis_court_reservation_system.mappers.ReservationMapper;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.Reservation;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.repository.CustomerRepository;
import com.xbalek.tennis_court_reservation_system.repository.ReservationRepository;
import com.xbalek.tennis_court_reservation_system.repository.TennisCourtRepository;
import com.xbalek.tennis_court_reservation_system.service.interfaces.ReservationServiceInterface;

@Service
public class ReservationService implements ReservationServiceInterface {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TennisCourtRepository tennisCourtRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public ReservationDTO findById(Long id) {
        return reservationMapper.toDTO(reservationRepository.findById(id));
    }

    @Override
    public BigDecimal create(Reservation reservation) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(reservation.getTennisCourt().getId());
        if (tennisCourt == null) {
            throw new NotFoundException("Tennis court not found");
        }
        if (tennisCourtRepository.isCourtReserved(tennisCourt.getId(), reservation.getStartTime(), reservation.getEndTime())) {
            throw new AlreadyReservedException("Tennis court is already reserved");
        }
        Customer customer;
        customer = customerRepository.findById(reservation.getCustomer().getId());
        if (customer == null) {
            customer = customerRepository.create(reservation.getCustomer());
        }
        reservation.setCustomer(customer);
        reservation.setTennisCourt(tennisCourt);
        Reservation result = reservationRepository.create(reservation);
        BigDecimal price = tennisCourt.getSurfaceType().getPricePerMinute().multiply(new BigDecimal(result.getDuration()));
        if (reservation.getIsDoubles()) {
            price.multiply(new BigDecimal(1.5));
        }
        return price;
    }

    @Override
    public ReservationDTO update(ReservationDTO reservationDetails) {
        return reservationMapper.toDTO(reservationRepository.update(reservationMapper.mapToReservation(reservationDetails)));
    }

    @Override
    public ReservationDTO delete(Long id) {
        return reservationMapper.toDTO(reservationRepository.delete(id));
    }
}