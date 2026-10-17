package org.rmagallangonzalez.hotel_reservation_system.hotel.exception;

public class RoomNotExistsException extends RuntimeException {
    public RoomNotExistsException(String message) {
        super(message);
    }
}
