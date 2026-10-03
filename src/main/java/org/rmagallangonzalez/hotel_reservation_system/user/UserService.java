package org.rmagallangonzalez.hotel_reservation_system.user;

import org.rmagallangonzalez.hotel_reservation_system.user.User.Role;
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

        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}
