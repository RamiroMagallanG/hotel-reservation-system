package org.rmagallangonzalez.hotel_reservation_system.reservation;

import java.net.URI;
import java.util.List;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.reservation.dto.ReservationRequestDTO;
import org.rmagallangonzalez.hotel_reservation_system.reservation.dto.ReservationResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping(ApiRoutes.Reservation.BASE)
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO dto, Authentication auth) {
        Reservation reservation = this.reservationService.createReservation(auth.getName(), dto);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(ApiRoutes.Reservation.GET_BY_ID_RESERVATION)
                        .queryParam("reservationId", reservation.getId())
                        .build().toUri();

        return ResponseEntity.created(location).body(ReservationResponseDTO.of(reservation));
    }
    
    @GetMapping(ApiRoutes.Reservation.BY_ID)
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable long reservationId) {
        Reservation reservation = this.reservationService.getReservation(reservationId);

        return ResponseEntity.ok(ReservationResponseDTO.of(reservation));
    }
    
    @GetMapping(ApiRoutes.Reservation.BY_ME)
    public ResponseEntity<List<ReservationResponseDTO>> getMineReservations(Authentication auth) {
        List<Reservation> reservations = this.reservationService.getReservations(auth.getName());

        return ResponseEntity.ok(reservations.stream().map(ReservationResponseDTO::of).toList());
    }
    
    @PutMapping(ApiRoutes.Reservation.BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putMethodName(@PathVariable long reservationId) {
        this.reservationService.cancelReservation(reservationId);
    }
}
