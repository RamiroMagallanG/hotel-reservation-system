package org.rmagallangonzalez.hotel_reservation_system.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u WHERE u.email = :email) THEN true ELSE false END")
    boolean existsByEmail(String email);
    
}
