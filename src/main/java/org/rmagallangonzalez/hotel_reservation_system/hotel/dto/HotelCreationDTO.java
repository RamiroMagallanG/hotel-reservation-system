package org.rmagallangonzalez.hotel_reservation_system.hotel.dto;

import org.rmagallangonzalez.hotel_reservation_system.hotel.Hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class HotelCreationDTO {
    @NotBlank(message = "The hotel name cannot be left blank")
    @Size(max = 256, message = "The hotel name must not exceed 256 characters")
    private String name;
    @NotBlank(message = "The hotel address cannot be left blank")
    @Size(max = 512, message = "The hotel address must not exceed 512 characters")
    private String address;

    public Hotel toHotel() {
        return new Hotel(
            this.name, this.address
        );
    }
}
