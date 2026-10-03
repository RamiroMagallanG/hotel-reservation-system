package org.rmagallangonzalez.hotel_reservation_system.userTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.rmagallangonzalez.hotel_reservation_system.user.UserService;
import org.rmagallangonzalez.hotel_reservation_system.user.User.Role;
import org.rmagallangonzalez.hotel_reservation_system.user.exception.EmailAlreadyExistsException;

/**
 * Unit Test for the User Service
 * 
 *  The UserRepository is mocked to isolate the test from the database
 * and to control the repository's behavior in each test
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private User user;
    
    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository);

        this.user = new User(
            "Prueba1",
            "prueba2",
            "prueba@example.com",
            "123456",
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
