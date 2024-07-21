package com.xbalek.tennis_court_reservation_system.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Reservation;

/**
 * Mapper for {@link Reservation}.
 *
 * @author Filip Balek
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDTO(Reservation reservation);

    Reservation mapToReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> toDTOList(List<Reservation> reservations);
    
}
