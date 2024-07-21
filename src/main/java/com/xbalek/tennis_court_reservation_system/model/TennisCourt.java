package com.xbalek.tennis_court_reservation_system.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class TennisCourt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter private String name;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "surface_id")
    @Setter private SurfaceType surfaceType;

    @OneToMany(mappedBy = "tennisCourt")
    private List<Reservation> reservations;

    public TennisCourt(SurfaceType surfaceType, String name) {
        this.surfaceType = surfaceType;
        this.name = name;
        this.isDeleted = false;
        this.reservations = List.of();
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void delete() {
        this.isDeleted = true;
    }
}
