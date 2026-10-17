package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.util.List;
import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.RoomCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.RoomDataDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.exception.RoomNotExistsException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomService(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    public List<Room> findRoomsByHotel(long hotelId) {
        return this.roomRepository.findByHotelId(hotelId);
    }

    public Room findRoomByHotelAndRoomId(long hotelId, long roomId) {
        Optional<Room> aux = this.roomRepository.findByIdAndHotelId(roomId, hotelId);

        if (aux.isEmpty()) {
            throw new RoomNotExistsException("The requested room does not exist");
        }

        return aux.get();
    }

    @Transactional 
    public Room createRoom(long hotelId, RoomCreationDTO dto) {
        Hotel hotel = this.hotelService.searchHotel(hotelId);

        Room room = dto.toRoom(hotel);

        return this.roomRepository.save(room);
    }

    @Transactional 
    public Room updateRoom(long hotelId, long roomId, RoomDataDTO dto) {
        Room room = this.findRoomByHotelAndRoomId(hotelId, roomId);

        room.setCapacity(dto.getCapacity());
        room.setNumber(dto.getNumber());
        room.setPricePerNight(dto.getPricePerNight());
        this.roomRepository.save(room);

        return room;
    }

    @Transactional
    public void deleteRoom(long hotelId, long roomId) {
        Room room = this.findRoomByHotelAndRoomId(hotelId, roomId);
        this.roomRepository.delete(room);
    }
}
