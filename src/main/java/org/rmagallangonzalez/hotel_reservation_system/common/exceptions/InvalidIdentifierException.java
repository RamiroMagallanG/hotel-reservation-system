package org.rmagallangonzalez.hotel_reservation_system.common.exceptions;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException(String message) {
        super(message);
    }
}