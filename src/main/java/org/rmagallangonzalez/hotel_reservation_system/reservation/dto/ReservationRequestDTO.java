package org.rmagallangonzalez.hotel_reservation_system.reservation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ReservationRequestDTO {
    @NotNull
    private long roomId;
    @NotNull 
    private long hotelId;
    @NotNull 
    private LocalDate checkin;
    @NotNull 
    private LocalDate checkout;
}
