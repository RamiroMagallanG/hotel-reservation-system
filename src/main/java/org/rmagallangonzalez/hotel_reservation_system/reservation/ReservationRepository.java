package org.rmagallangonzalez.hotel_reservation_system.reservation;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("""
        SELECT count(r) > 0 FROM reservation r
        WHERE r.room.id = :roomId
            AND r.status = 'CONFIRMED'
            AND r.checkIn < :checkOut
            AND r.checkOut > :checkIn
    """)
    boolean existsOvelappingOfReservations(long roomId, LocalDate checkIn, LocalDate checkOut);
}
