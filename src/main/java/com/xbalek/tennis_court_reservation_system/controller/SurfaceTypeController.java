package com.xbalek.tennis_court_reservation_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.interfaces.SurfaceTypeServiceInterface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/surface-types")
public class SurfaceTypeController {
    
    @Autowired
    private SurfaceTypeServiceInterface surfaceTypeService;

    @Autowired
    private JWTHeaderValidator jwtHeaderValidator;

    @GetMapping("/{id}")
    public ResponseEntity<SurfaceTypeDTO> findById(@PathVariable Long id) {
        SurfaceTypeDTO surfaceType = surfaceTypeService.findById(id);
        if (surfaceType == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(surfaceType);
    }

    @PostMapping
    public ResponseEntity<SurfaceTypeDTO> create(@RequestBody SurfaceType surfaceType, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        SurfaceTypeDTO createdSurface = surfaceTypeService.create(surfaceType);
        return ResponseEntity.ok(createdSurface);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurfaceTypeDTO> update(@PathVariable Long id, @RequestBody SurfaceTypeDTO surfaceTypeDetails, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        SurfaceTypeDTO updatedSurface = surfaceTypeService.update(id, surfaceTypeDetails);
        if (updatedSurface == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSurface);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SurfaceTypeDTO> delete(@PathVariable Long id, @RequestBody Long adminId, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        SurfaceTypeDTO deletedSurface = surfaceTypeService.delete(id);
        if (deletedSurface == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedSurface);
    }

    @GetMapping("/")
    @Transactional
    public ResponseEntity<SurfaceTypeDTO[]> getAll() {
        SurfaceTypeDTO[] surfaceTypes = surfaceTypeService.getAll();
        return ResponseEntity.ok(surfaceTypes);
    }
}
