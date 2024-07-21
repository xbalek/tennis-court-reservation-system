package com.xbalek.tennis_court_reservation_system.init;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbalek.tennis_court_reservation_system.configuration.AppConfig;
import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.CustomerRole;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;
import com.xbalek.tennis_court_reservation_system.model.SurfaceTypeEnum;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.service.CustomerService;
import com.xbalek.tennis_court_reservation_system.service.SurfaceTypeService;
import com.xbalek.tennis_court_reservation_system.service.TennisCourtService;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private TennisCourtService courtService;

    @Autowired
    private SurfaceTypeService surfaceTypeService;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void initData() {

        if (appConfig.isInitializeData()) {

            // Initialize two types of surfaces
            SurfaceType clay = new SurfaceType(SurfaceTypeEnum.CLAY,  new BigDecimal(3.0));
            SurfaceTypeDTO surface1 = surfaceTypeService.create(clay);
            SurfaceType hard = new SurfaceType(SurfaceTypeEnum.HARD, new BigDecimal(3.5));
            SurfaceTypeDTO surface2 = surfaceTypeService.create(hard);

            // Initialize four courts
            TennisCourt court1 = new TennisCourt(null, "Court 1");
            courtService.create(court1, surface1.getId());
            TennisCourt court2 = new TennisCourt(null, "Court 2");
            courtService.create(court2, surface1.getId());
            TennisCourt court3 = new TennisCourt(null, "Court 3");
            courtService.create(court3, surface2.getId());
            TennisCourt court4 = new TennisCourt(null, "Court 4");
            courtService.create(court4, surface2.getId());

            Customer customer1 = new Customer("fildo", "123456789");
            customer1.setRole(CustomerRole.ADMIN);
            customerService.create(customer1);


            System.out.println("Data initialized successfully.");
        }
    }
}
