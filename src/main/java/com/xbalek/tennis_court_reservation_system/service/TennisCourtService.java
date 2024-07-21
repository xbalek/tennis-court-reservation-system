package com.xbalek.tennis_court_reservation_system.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.dto.TennisCourtDTO;
import com.xbalek.tennis_court_reservation_system.mappers.TennisCourtMapper;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.repository.ReservationRepository;
import com.xbalek.tennis_court_reservation_system.repository.TennisCourtRepository;
import com.xbalek.tennis_court_reservation_system.service.interfaces.TennisCourtServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class TennisCourtService implements TennisCourtServiceInterface {

    @Autowired
    private TennisCourtRepository tennisCourtRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TennisCourtMapper tennisCourtMapper;

    @Override
    public TennisCourtDTO findById(Long id) {
        return tennisCourtMapper.toDTO(tennisCourtRepository.findById(id));
    }

    @Override
    public TennisCourtDTO create(TennisCourt tennisCourt, Long surfaceTypeId) {
        return tennisCourtMapper.toDTO(tennisCourtRepository.create(tennisCourt, surfaceTypeId));
    }

    @Override
    public TennisCourtDTO update(Long id, TennisCourtDTO TenniscourtDetails) {
        TennisCourt court = tennisCourtMapper.mapToTennisCourt(TenniscourtDetails);
        return tennisCourtMapper.toDTO(tennisCourtRepository.update(court));
    }

    @Override
    @Transactional
    public TennisCourtDTO delete(Long id) {
        ReservationDTO[] reservations = tennisCourtRepository.getReservations(id);
        for (ReservationDTO reservation : reservations) {
            reservationRepository.delete(reservation.getId());
        }
        return tennisCourtMapper.toDTO(tennisCourtRepository.delete(id));
    }

    @Override
    public ReservationDTO[] getReservations(Long id) {
        return tennisCourtRepository.getReservations(id);
    }

    @Override
    public boolean isCourtReserved(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        return tennisCourtRepository.isCourtReserved(id, startTime, endTime);
    }

    @Override
    @Transactional
    public TennisCourtDTO[] getAll() {
        return tennisCourtMapper.toDTOArray(tennisCourtRepository.getAll());
    }
    
}
