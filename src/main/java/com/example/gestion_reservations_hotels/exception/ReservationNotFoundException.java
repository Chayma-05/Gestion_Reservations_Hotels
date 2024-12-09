package com.example.gestion_reservations_hotels.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
} 