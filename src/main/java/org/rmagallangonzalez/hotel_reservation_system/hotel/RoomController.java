package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.net.URI;
import java.util.List;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.RoomCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.RoomDataDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping(ApiRoutes.Hotel.Room.BASE)
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDataDTO>> getRooms(@PathVariable long hotelId) {
        List<Room> rooms = this.roomService.findRoomsByHotel(hotelId);

        return ResponseEntity.ok(rooms.stream().map(RoomDataDTO::of).toList());
    }

    @GetMapping(ApiRoutes.Hotel.Room.BY_ID)
    public ResponseEntity<RoomDataDTO> getMethodName(@PathVariable long hotelId, @PathVariable long roomId) {
        Room room = this.roomService.findRoomByHotelAndRoomId(hotelId, roomId);

        return ResponseEntity.ok(RoomDataDTO.of(room));
    }
    
    @PostMapping
    public ResponseEntity<RoomDataDTO> postMethodName(@PathVariable long hotelId, @RequestBody RoomCreationDTO dto) {
        Room room = this.roomService.createRoom(hotelId, dto);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(ApiRoutes.Hotel.Room.BASE)
                        .queryParam("hotelId", hotelId)
                        .queryParam("roomId", room.getId())
                        .build().toUri();
        
        return ResponseEntity.created(location).body(RoomDataDTO.of(room));
    }
    
    @PutMapping(ApiRoutes.Hotel.Room.BY_ID)
    public ResponseEntity<RoomDataDTO> updateRoom(@PathVariable long hotelId, @PathVariable long roomId, @RequestBody RoomDataDTO dto) {
        Room room = this.roomService.updateRoom(hotelId, roomId, dto);

        return ResponseEntity.accepted().body(RoomDataDTO.of(room));
    }

    @DeleteMapping(ApiRoutes.Hotel.Room.BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable long hotelId, @PathVariable long roomId) {
        this.deleteRoom(hotelId, roomId);
    }
}
