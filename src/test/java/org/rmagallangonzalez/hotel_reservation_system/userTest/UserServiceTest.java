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
import org.rmagallangonzalez.hotel_reservation_system.user.User.Role;
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
    @MockitoBean
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    private User user;
    
    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, passwordEncoder);

        this.user = new User(
            "Prueba1",
            "prueba2",
            "prueba@example.com",
            "ContraseñaValida1",
            Role.USER,
            LocalDate.now()
        );
    }

    @Test
    void saveUserWhenEmailIsNotRegisteredTest() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void saveUserWhenEmailIsAlreadyRegisteredTest() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        
        assertThrows(EmailAlreadyExistsException.class, () -> userService.save(user));

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository, never()).save(user);
    }
}
