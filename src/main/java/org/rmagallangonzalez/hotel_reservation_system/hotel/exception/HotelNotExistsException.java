package org.rmagallangonzalez.hotel_reservation_system.hotel.exception;

public class HotelNotExistsException extends RuntimeException {
    public HotelNotExistsException(String message) {
        super(message);
    }
}

