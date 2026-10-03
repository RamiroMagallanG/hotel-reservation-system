package org.rmagallangonzalez.hotel_reservation_system.userTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration test for the registration endpoint
 * 
 * The UserRepository is mocked to isolate the tests from the database
 * and to allow precise control over its behavior in each test scenario.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class UserRegisterTest {
    private static final String FIRST_NAME = "Prueba1";
    private static final String LAST_NAME = "Prueba1";
    private static final String EMAIL = "prueba@example.com";
    private static final String PASSWORD = "123456";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2005, 5, 5);

    @MockitoBean
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MockMvc mockMvc;

    private ResultActions performRegistration() throws Exception{
        return mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                    """
                    {
                        "email": "%s",
                        "password": "%s",
                        "firstName": "%s",
                        "lastName": "%s",
                        "dateOfBirth": "%s"
                    }
                    """, 
                    EMAIL,
                    PASSWORD,
                    FIRST_NAME,
                    LAST_NAME,
                    DATE_OF_BIRTH
                )
            )
        );
    }

    @Test
    void registerNewUserTest() throws Exception{
        when(userRepository.save(any(User.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        performRegistration()
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                .value(FIRST_NAME)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                .value(LAST_NAME)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                .value(EMAIL)
        );

        // I use ArgumentCaptor instead of verify(userRepository).save(user) because the
        // user created by the controller has a different password hash
        // than the one in the test, due to the random salt used by the PasswordEncoder.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User userCaptured = userCaptor.getValue();
        assertTrue(passwordEncoder.matches(PASSWORD, userCaptured.getPassword()));
    }

    @Test
    void registerNewUserWithRepeatedEmailTest() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        performRegistration()
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content()
                .string("Email 'prueba@example.com' already exists")
        );
    }
}
