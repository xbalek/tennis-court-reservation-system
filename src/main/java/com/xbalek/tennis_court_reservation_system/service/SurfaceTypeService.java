package com.xbalek.tennis_court_reservation_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;
import com.xbalek.tennis_court_reservation_system.repository.interfaces.SurfaceTypeRepositoryInterface;
import com.xbalek.tennis_court_reservation_system.service.interfaces.SurfaceTypeServiceInterface;

@Service
public class SurfaceTypeService implements SurfaceTypeServiceInterface {
    
    @Autowired
    private SurfaceTypeRepositoryInterface surfaceTypeRepository;

    @Override
    public SurfaceTypeDTO findById(Long id) {
        return surfaceTypeRepository.findById(id);
    }

    @Override
    public SurfaceTypeDTO create(SurfaceType surfaceType) {
        return surfaceTypeRepository.create(surfaceType);
    }

    @Override
    public SurfaceTypeDTO update(Long id, SurfaceTypeDTO surfaceTypeDetails) {
        return surfaceTypeRepository.update(surfaceTypeDetails);
    }

    @Override
    public SurfaceTypeDTO delete(Long id) {
        return surfaceTypeRepository.delete(id);
    }

    @Override
    public SurfaceTypeDTO[] getAll() {
        return surfaceTypeRepository.getAll();
    }
}
