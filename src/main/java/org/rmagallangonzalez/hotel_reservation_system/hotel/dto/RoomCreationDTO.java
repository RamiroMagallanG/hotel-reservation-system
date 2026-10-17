package org.rmagallangonzalez.hotel_reservation_system.hotel.dto;

import java.math.BigDecimal;

import org.rmagallangonzalez.hotel_reservation_system.hotel.Hotel;
import org.rmagallangonzalez.hotel_reservation_system.hotel.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class RoomCreationDTO {
    private String number;
    private int capacity;
    private BigDecimal pricePerNight;

    public Room toRoom(Hotel hotel) {
        return new Room(hotel, number, capacity, pricePerNight);
    }
}
