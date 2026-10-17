package org.rmagallangonzalez.hotel_reservation_system.hotel;

import org.springframework.stereotype.Component;

@Component("hotelSecurity")
public class HotelSecurity {
    private final StaffRepository staffRepository;

    public HotelSecurity(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public boolean isManager(long hotelId, long userID) {
        return this.staffRepository.existsByHotelIdAndUserIdAndStaffRole(hotelId, userID, StaffRole.MANAGER);
    }

    public boolean isStaff(long hotelId, long userId) {
        return this.staffRepository.existsByHotelIdAndUserId(hotelId, userId);
    }
}
