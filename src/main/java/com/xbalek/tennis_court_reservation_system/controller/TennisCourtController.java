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

import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.dto.TennisCourtDTO;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.TennisCourtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/tennis-courts")
public class TennisCourtController {

    @Autowired
    private TennisCourtService tennisCourtService;

    @Autowired
    private JWTHeaderValidator jwtHeaderValidator;

    @GetMapping("/{id}")
    public ResponseEntity<TennisCourtDTO> findById(@PathVariable Long id) {
        TennisCourtDTO tennisCourt = tennisCourtService.findById(id);
        if (tennisCourt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tennisCourt);
    }

    @PostMapping
    public ResponseEntity<TennisCourtDTO> create(@RequestBody TennisCourt tennisCourt, Long surfaceTypeId, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        TennisCourtDTO createdCourt = tennisCourtService.create(tennisCourt, surfaceTypeId);
        return ResponseEntity.ok(createdCourt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TennisCourtDTO> update(@PathVariable Long id, @RequestBody TennisCourtDTO tennisCourtDetails,  HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        TennisCourtDTO updatedCourt = tennisCourtService.update(id, tennisCourtDetails);
        if (updatedCourt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCourt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TennisCourtDTO> delete(@PathVariable Long id, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        TennisCourtDTO deletedCourt = tennisCourtService.delete(id);
        if (deletedCourt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedCourt);
    }

    @GetMapping("/{id}/reservations")
    @Transactional
    public ResponseEntity<ReservationDTO[]> getReservations(@PathVariable Long id, HttpServletRequest request) {
        if (!jwtHeaderValidator.isAdminFromRequest(request)) {
            return ResponseEntity.status(403).build();
        }
        ReservationDTO[] reservations = tennisCourtService.getReservations(id);
        if (reservations == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/")
    @Transactional
    public ResponseEntity<TennisCourtDTO[]> getAll() {
        TennisCourtDTO[] tennisCourts = tennisCourtService.getAll();
        return ResponseEntity.ok(tennisCourts);
    }
}