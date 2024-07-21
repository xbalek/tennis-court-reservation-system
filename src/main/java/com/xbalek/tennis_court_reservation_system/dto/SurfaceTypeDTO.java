package com.xbalek.tennis_court_reservation_system.dto;

import java.math.BigDecimal;

import com.xbalek.tennis_court_reservation_system.model.SurfaceTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurfaceTypeDTO {

    private final Long id;

    private final SurfaceTypeEnum name;

    private final BigDecimal pricePerMinute;
}
