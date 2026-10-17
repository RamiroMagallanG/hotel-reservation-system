package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelDataDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.exception.HotelNotExistsException;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service 
public class HotelService {
    private final HotelRepository hotelRepository;
    private final StaffRepository hotelStaffRepository;

    public HotelService(HotelRepository hotelRepository, StaffRepository hotelStaffRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelStaffRepository = hotelStaffRepository;
    }

    @Transactional 
    public Hotel registerNewHotel(HotelCreationDTO dto, User user) { 
        Hotel hotel = this.hotelRepository.save(dto.toHotel());

        Staff hotelStaff = new Staff();
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

    @Transactional 
    public Hotel updateHotel(long hotelId, HotelDataDTO dto) {
        Optional<Hotel> aux = this.hotelRepository.findById(hotelId);

        if (aux.isEmpty()) {
            throw new HotelNotExistsException("Hotel with id " + hotelId + " not exists");
        }

        Hotel hotel = aux.get();
        hotel.setAddress(dto.getAddress());
        hotel.setName(dto.getName());
        this.hotelRepository.save(hotel);

        return hotel;
    }

    @Transactional
    public void deleteHotel(long hotelIde) {
        Hotel hotel = this.searchHotel(hotelIde);
        this.hotelRepository.delete(hotel);
    }
}
