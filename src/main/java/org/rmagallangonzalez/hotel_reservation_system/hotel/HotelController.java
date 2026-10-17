package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.net.URI;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelDataDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping(ApiRoutes.Hotel.BASE)
public class HotelController {
    private final HotelService hotelService;
    private final UserService userService;

    public HotelController(HotelService hotelService, UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }

    @PostMapping(ApiRoutes.Hotel.CREATE)
    public ResponseEntity<HotelDataDTO> createNewHotel(@Valid @RequestBody HotelCreationDTO dto) { 
        User userLogged = this.userService.findUser(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
        Hotel hotel = this.hotelService.registerNewHotel(dto, userLogged);

        Staff hotelStaff = new Staff();
        hotelStaff.setHotel(hotel);
        hotelStaff.setUser(userLogged);
        hotelStaff.setStaffRole(StaffRole.MANAGER);
        
        URI location = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(ApiRoutes.Hotel.BASE)
                        .queryParam("id", hotel.getId())
                        .build().toUri();

        return ResponseEntity.created(location).body(new HotelDataDTO(hotel));
    }
    
    @GetMapping(ApiRoutes.Hotel.BY_ID)
    public ResponseEntity<HotelDataDTO> getMethodName(@PathVariable Long id) {
        return ResponseEntity.ok(new HotelDataDTO(this.hotelService.searchHotel(id)));
    }

    @PutMapping(ApiRoutes.Hotel.BY_ID)
    @PreAuthorize("hasRole('ADMIN') or @hotelSecurity.isManager(#id, authentication)")
    public ResponseEntity<HotelDataDTO> modifyHotel(@PathVariable Long id, @RequestBody HotelDataDTO dto) {
        Hotel hotel = this.hotelService.updateHotel(id, dto);
        
        return ResponseEntity.accepted().body(new HotelDataDTO(hotel));
    }

    @DeleteMapping(ApiRoutes.Hotel.BY_ID)
    @PreAuthorize("hasRole('ADMIN') or @hotelSecurity.isManager(#id, authentication)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        this.hotelService.deleteHotel(id);
    }
}
