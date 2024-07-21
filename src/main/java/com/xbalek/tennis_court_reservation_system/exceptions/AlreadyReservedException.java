package com.xbalek.tennis_court_reservation_system.exceptions;

public class AlreadyReservedException extends RuntimeException {
    
    public AlreadyReservedException(String message) {
        super(message);
    }
}
