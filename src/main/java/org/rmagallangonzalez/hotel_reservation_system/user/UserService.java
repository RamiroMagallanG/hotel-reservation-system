package org.rmagallangonzalez.hotel_reservation_system.user;

import org.rmagallangonzalez.hotel_reservation_system.user.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + user.getEmail() + "' already exists");
        }

        return userRepository.save(user);
    }
}
