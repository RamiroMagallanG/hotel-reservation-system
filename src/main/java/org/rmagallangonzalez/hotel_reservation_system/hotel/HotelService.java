package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.exception.HotelNotExistsException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service 
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Transactional 
    public Hotel registerNewHotel(HotelCreationDTO dto) {
        return this.hotelRepository.save(dto.toHotel());
    }

    public Hotel searchHotel(long id) {
        Optional<Hotel> hotel = this.hotelRepository.findById(id);

        if (hotel.isEmpty()) 
            throw new HotelNotExistsException("The hotel with ID " + id + " does not exist");

        return hotel.get();
    }
}
