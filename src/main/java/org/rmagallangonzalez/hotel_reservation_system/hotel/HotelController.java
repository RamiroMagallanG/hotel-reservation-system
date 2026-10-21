package org.rmagallangonzalez.hotel_reservation_system.hotel;

import java.net.URI;
import java.util.List;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelCreationDTO;
import org.rmagallangonzalez.hotel_reservation_system.hotel.dto.HotelDataDTO;
import org.rmagallangonzalez.hotel_reservation_system.reservation.Reservation;
import org.rmagallangonzalez.hotel_reservation_system.reservation.ReservationService;
import org.rmagallangonzalez.hotel_reservation_system.reservation.dto.ReservationResponseDTO;
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
    private final ReservationService reservationService;

    public HotelController(HotelService hotelService, UserService userService, ReservationService reservationService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.reservationService = reservationService;
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
    public ResponseEntity<HotelDataDTO> getMethodName(@PathVariable Long hotelId) {
        return ResponseEntity.ok(new HotelDataDTO(this.hotelService.searchHotel(hotelId)));
    }

    @PutMapping(ApiRoutes.Hotel.BY_ID)
    @PreAuthorize("hasRole('ADMIN') or @hotelSecurity.isManager(#hotelId, authentication)")
    public ResponseEntity<HotelDataDTO> modifyHotel(@PathVariable Long hotelId, @RequestBody HotelDataDTO dto) {
        Hotel hotel = this.hotelService.updateHotel(hotelId, dto);
        
        return ResponseEntity.accepted().body(new HotelDataDTO(hotel));
    }

    @DeleteMapping(ApiRoutes.Hotel.BY_ID)
    @PreAuthorize("hasRole('ADMIN') or @hotelSecurity.isManager(#hotelId, authentication)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long hotelId) {
        this.hotelService.deleteHotel(hotelId);
    }

    @GetMapping(ApiRoutes.Hotel.GET_RESERVATIONS_BY_HOTEL_ID)
    @PreAuthorize("hasRole('ADMIN') or @hotelSecurity.isManager(#hotelId, authentication)")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByHotel(@PathVariable long hotelId) {
        List<Reservation> reservationsByHotel = this.reservationService.getReservations(hotelId);

        return ResponseEntity.ok(reservationsByHotel.stream().map(ReservationResponseDTO::of).toList());
    }
}
