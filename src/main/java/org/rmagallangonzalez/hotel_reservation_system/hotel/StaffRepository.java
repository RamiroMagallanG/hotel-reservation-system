package org.rmagallangonzalez.hotel_reservation_system.hotel;

import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Long> {
    
    boolean existsByHotelIdAndUserIdAndStaffRole(long hotelId, long userId, StaffRole staffRole);
}
