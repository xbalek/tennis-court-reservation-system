package com.xbalek.tennis_court_reservation_system.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${app.initialize-data}")
    private boolean initializeData;

    public boolean isInitializeData() {
        return initializeData;
    }
}