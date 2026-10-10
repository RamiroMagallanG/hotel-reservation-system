package org.rmagallangonzalez.hotel_reservation_system.reservation;

import org.springframework.stereotype.Service;

@Service 
public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    
}
