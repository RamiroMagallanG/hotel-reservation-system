package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.exception.HotelNotExistsException;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service 
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelStaffRepository hotelStaffRepository;

    public HotelService(HotelRepository hotelRepository, HotelStaffRepository hotelStaffRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelStaffRepository = hotelStaffRepository;
    }

    @Transactional 
    public Hotel registerNewHotel(HotelCreationDTO dto, User user) { 
        Hotel hotel = this.hotelRepository.save(dto.toHotel());

        HotelStaff hotelStaff = new HotelStaff();
        hotelStaff.setHotel(hotel);
        hotelStaff.setUser(user);
        hotelStaff.setStaffRole(StaffRole.MANAGER);

        this.hotelStaffRepository.save(hotelStaff);

        return hotel;
    }

    public Hotel searchHotel(long id) {
        Optional<Hotel> hotel = this.hotelRepository.findById(id);

        if (hotel.isEmpty()) 
            throw new HotelNotExistsException("The hotel with ID " + id + " does not exist");

        return hotel.get();
    }
}
