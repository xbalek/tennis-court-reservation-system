package com.xbalek.tennis_court_reservation_system.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class SurfaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private SurfaceTypeEnum name;
    
    @Setter private BigDecimal pricePerMinute;
    private Boolean isDeleted;

    public SurfaceType(SurfaceTypeEnum name, BigDecimal pricePerMinute) {
        this.name = name;
        this.pricePerMinute = pricePerMinute;
        this.isDeleted = false;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
