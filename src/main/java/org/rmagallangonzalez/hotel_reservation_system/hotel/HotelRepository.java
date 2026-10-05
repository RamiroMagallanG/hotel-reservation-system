package org.rmagallangonzalez.hotel_reservation_system.hotel;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface HotelRepository extends CrudRepository<Hotel, Long> {
    Optional<Hotel> findById(long id);
}
