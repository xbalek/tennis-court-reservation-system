package com.xbalek.tennis_court_reservation_system.repository.interfaces;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;

public interface SurfaceTypeRepositoryInterface {
    SurfaceTypeDTO findById(Long id);
    SurfaceTypeDTO create(SurfaceType surfaceType);
    SurfaceTypeDTO update(SurfaceTypeDTO surfaceType);
    SurfaceTypeDTO delete(Long id);
    SurfaceTypeDTO[] getAll();
}
