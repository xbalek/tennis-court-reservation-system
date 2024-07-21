package com.xbalek.tennis_court_reservation_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTHeaderValidator {

    @Autowired
    private JWTProvider tokenProvider;

    @Autowired
    private CustomerServiceInterface customerService;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER = "Bearer ";

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null || !token.startsWith(BEARER)) {
            return null;
        }
        return token.substring(BEARER.length());
    }
    
    public boolean validateRequest(HttpServletRequest request, Long providedId) {
        String token = getTokenFromRequest(request);
        if (token == null) {
            return false;
        }
        return tokenProvider.validateToken(token, providedId);
    }

    public boolean isAdminFromRequest(HttpServletRequest httpServletRequest) {
        String token = getTokenFromRequest(httpServletRequest);
        if (token == null) {
            return false;
        }
        Long id = tokenProvider.getUserIdFromJWT(token);
        if (id == null) {
            return false;
        }

        return customerService.isAdmin(id);
    }
}
