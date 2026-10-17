package org.rmagallangonzalez.hotel_reservation_system.hotel;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;


public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByHotelId(long hotelId);

    Optional<Room> findByIdAndHotelId(long id, long hotelId);
}
