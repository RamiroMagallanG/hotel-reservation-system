package org.rmagallangonzalez.hotel_reservation_system.reservation;

import java.util.List;
import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.hotel.Room;
import org.rmagallangonzalez.hotel_reservation_system.hotel.RoomService;
import org.rmagallangonzalez.hotel_reservation_system.reservation.dto.ReservationRequestDTO;
import org.rmagallangonzalez.hotel_reservation_system.reservation.exceptions.ReservationNotFoundException;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service 
public class ReservationService {
    private ReservationRepository reservationRepository;
    private RoomService roomService;
    private UserService userService;

    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
    }

    @Transactional 
    public Reservation createReservation(String userEmail, ReservationRequestDTO dto) {
        User user = this.userService.findUser(userEmail);
        Room room = this.roomService.findRoomByHotelAndRoomId(dto.getHotelId(), dto.getRoomId());
        
        Reservation reservation = new Reservation(user, room, dto.getCheckin(), dto.getCheckout());

        return this.reservationRepository.save(reservation);
    }

    public Reservation getReservation(long id) {
        Optional<Reservation> aux = this.reservationRepository.findById(id);

        if (aux.isEmpty()) {
            throw new ReservationNotFoundException("Reservation with id '" + id + "' not found");
        }

        return aux.get();
    }

    public List<Reservation> getReservations(String userEmail) {
        return this.reservationRepository.findByUserEmail(userEmail);
    }

    public List<Reservation> getReservations(long hotelId) {
        return this.reservationRepository.findByRoomHotelId(hotelId);
    }

    public void cancelReservation(long reservationId) {
        Reservation reservation = this.getReservation(reservationId);

        reservation.setStatus(ReservationStatus.CANCELLED);

        this.reservationRepository.save(reservation);
    }
    
}
