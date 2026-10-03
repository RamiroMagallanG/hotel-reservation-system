package org.rmagallangonzalez.hotel_reservation_system.userTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.rmagallangonzalez.hotel_reservation_system.user.UserService;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserRegistrationDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.exception.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Unit Test for the User Service
 * 
 *  The UserRepository is mocked to isolate the test from the database
 * and to control the repository's behavior in each test
 */
@SpringBootTest
@Import(SecurityConfig.class)
public class UserServiceTest {
    private final static String PASSWORD = "ValidPassword1";

    @MockitoBean
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    private User user;
    private UserRegistrationDTO userDTO;
    
    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, passwordEncoder);

        this.user = new User(
            "Prueba1",
            "prueba2",
            "prueba@example.com",
            passwordEncoder.encode(PASSWORD),
            LocalDate.of(2005, 5, 5)
        );

        this.userDTO = new UserRegistrationDTO(
            this.user.getFirstName(),
            this.user.getLastName(),
            this.user.getEmail(),
            PASSWORD,
            this.user.getDateOfBirth()
        );
    }

    @Test
    void saveUserWhenEmailIsNotRegisteredTest() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerNewUser(userDTO);

        assertEquals(user, result);
        verify(userRepository).save(user);
        verify(userRepository).existsByEmail(userDTO.getEmail());
    }

    @Test
    void saveUserWhenEmailIsAlreadyRegisteredTest() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        
        assertThrows(EmailAlreadyExistsException.class, 
            () -> userService.registerNewUser(userDTO));

        verify(userRepository).existsByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(user);
    }
}
