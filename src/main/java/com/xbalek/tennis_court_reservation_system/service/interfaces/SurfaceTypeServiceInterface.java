package com.xbalek.tennis_court_reservation_system.service.interfaces;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;

public interface SurfaceTypeServiceInterface {
    SurfaceTypeDTO findById(Long id);
    SurfaceTypeDTO create(SurfaceType surfaceType);
    SurfaceTypeDTO update(Long id, SurfaceTypeDTO surfaceTypeDetails);
    SurfaceTypeDTO delete(Long id);
    SurfaceTypeDTO[] getAll();
}
