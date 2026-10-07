package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.net.URI;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelDataDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping(ApiRoutes.Hotel.BASE)
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDataDTO> getMethodName(@PathVariable Long id) {
        return ResponseEntity.ok(new HotelDataDTO(this.hotelService.searchHotel(id)));
    }

    @PostMapping(ApiRoutes.Hotel.CREATE)
    public ResponseEntity<HotelDataDTO> createNewHotel(@Valid @RequestBody HotelCreationDTO dto) { 
        Hotel hotel = this.hotelService.registerNewHotel(dto);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(ApiRoutes.Hotel.BASE)
                        .queryParam("id", hotel.getId())
                        .build().toUri();

        return ResponseEntity.created(location).body(new HotelDataDTO(hotel));
    }
    
}
