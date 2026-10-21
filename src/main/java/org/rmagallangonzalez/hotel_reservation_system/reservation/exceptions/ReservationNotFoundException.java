package org.rmagallangonzalez.hotel_reservation_system.reservation.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
