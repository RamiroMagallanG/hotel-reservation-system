package org.rmagallangonzalez.hotel_reservation_system.user;

import java.util.Optional;

import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserRegistrationDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.exception.EmailAlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerNewUser(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + dto.getEmail() + "' already exists");
        }

        User user = new User(
            dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            passwordEncoder.encode(dto.getPassword()),
            UserRole.CLIENT,
            dto.getDateOfBirth()
        );

        return userRepository.save(user);
    }

    public User findUser(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if (user.isEmpty())
            throw new BadCredentialsException("User with email '" + email + "' not exists");

        return user.get();
    }
}
