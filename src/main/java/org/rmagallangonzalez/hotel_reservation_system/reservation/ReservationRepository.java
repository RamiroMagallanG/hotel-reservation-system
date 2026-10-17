package org.rmagallangonzalez.hotel_reservation_system.reservation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByUserId(long userId);

    List<Reservation> findByRoomHotelId(long id);

    @Query("""
        SELECT count(r) > 0 FROM Reservation r
        WHERE r.room.id = :roomId
            AND r.status = 'CONFIRMED'
            AND r.checkIn < :checkOut
            AND r.checkOut > :checkIn
    """)
    boolean existsOvelappingOfReservations(long roomId, LocalDate checkIn, LocalDate checkOut);
}
