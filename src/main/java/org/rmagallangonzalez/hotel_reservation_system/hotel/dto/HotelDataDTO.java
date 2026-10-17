package org.rmagallangonzalez.hotel_reservation_system.hotel.dto;

import org.rmagallangonzalez.hotel_reservation_system.hotel.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class HotelDataDTO {
    private String name;
    private String address;

    public HotelDataDTO(Hotel hotel) {
        this.name = hotel.getName();
        this.address = hotel.getAddress();
    }
}
