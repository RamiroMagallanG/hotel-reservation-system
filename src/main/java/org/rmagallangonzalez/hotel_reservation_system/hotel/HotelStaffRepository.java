package org.rmagallangonzalez.hotel_reservation_system.hotel;

import org.springframework.data.repository.CrudRepository;

public interface HotelStaffRepository extends CrudRepository<HotelStaff, Long> {
    
    boolean existsByHotelIdAndUserIdAndStaffRole(long hotelId, long userId, StaffRole staffRole);
}
