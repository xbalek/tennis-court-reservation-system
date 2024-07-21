package com.xbalek.tennis_court_reservation_system.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.xbalek.tennis_court_reservation_system.dto.TennisCourtDTO;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;

/**
 * Mapper for {@link TennisCourt}.
 *
 * @author Filip Balek
 */
@Mapper(componentModel = "spring")
public interface TennisCourtMapper {
    
    TennisCourtDTO toDTO(TennisCourt tennisCourt);

    @Mapping(target = "reservations", ignore = true)
    TennisCourt mapToTennisCourt(TennisCourtDTO tennisCourtDTO);

    List<TennisCourtDTO> toDTOList(List<TennisCourt> tennisCourts);

    TennisCourtDTO[] toDTOArray(TennisCourt[] tennisCourts);
}
