package org.rmagallangonzalez.hotel_reservation_system.reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
