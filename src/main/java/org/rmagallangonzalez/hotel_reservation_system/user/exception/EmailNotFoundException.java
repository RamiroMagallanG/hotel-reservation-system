package org.rmagallangonzalez.hotel_reservation_system.user.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
