package org.rmagallangonzalez.hotel_reservation_system.reservation.dto;

import java.time.LocalDate;

import org.rmagallangonzalez.hotel_reservation_system.reservation.Reservation;
import org.rmagallangonzalez.hotel_reservation_system.reservation.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ReservationResponseDTO {
    private long id;
    private long roomId;
    private long hotelId;
    private long clientId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ReservationStatus status;

    public static ReservationResponseDTO of(Reservation reservation) {
        return new ReservationResponseDTO(
            reservation.getId(),
            reservation.getRoom().getId(),
            reservation.getRoom().getHotel().getId(), 
            reservation.getUser().getId(),
            reservation.getCheckIn(),
            reservation.getCheckOut(),
            reservation.getStatus()
        );
    }
}
