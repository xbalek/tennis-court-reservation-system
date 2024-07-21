package com.xbalek.tennis_court_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TennisCourtDTO {
    
    final private Long id;
    
    final private String name;
    
    final private SurfaceTypeDTO surfaceType;
}
