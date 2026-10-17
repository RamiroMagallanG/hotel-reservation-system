package org.rmagallangonzalez.hotel_reservation_system.hotel.dto;

import java.math.BigDecimal;

import org.rmagallangonzalez.hotel_reservation_system.hotel.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class RoomDataDTO {
    private long id;
    private String number;
    private int capacity;
    private BigDecimal pricePerNight;

    public RoomDataDTO(Room room) {
        this.id = room.getId();
        this.number = room.getNumber();
        this.capacity = room.getCapacity();
        this.pricePerNight = room.getPricePerNight();
    }

    public static RoomDataDTO of(Room room) {
        return new RoomDataDTO(room);
    }
}
