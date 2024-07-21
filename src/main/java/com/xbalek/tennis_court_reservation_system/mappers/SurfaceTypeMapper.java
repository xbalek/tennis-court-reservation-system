package com.xbalek.tennis_court_reservation_system.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;

/**
 * Mapper for {@link SurfaceType}.
 *
 * @author Filip Balek
 */
@Mapper(componentModel = "spring")
public interface SurfaceTypeMapper {
    
    SurfaceTypeDTO toDTO(SurfaceType surfaceType);

    SurfaceType mapToSurfaceType(SurfaceTypeDTO surfaceTypeDTO);

    List<SurfaceTypeDTO> toDTOList(List<SurfaceType> surfaceTypes);
}
