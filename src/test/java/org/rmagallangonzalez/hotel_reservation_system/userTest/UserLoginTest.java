package org.rmagallangonzalez.hotel_reservation_system.userTest;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.rmagallangonzalez.hotel_reservation_system.user.User.Role;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * Integration tests for the login endpoint.
 *
 *  @SpringBootTest is used to set up the Spring context, and
 * @AutoConfigureMockMvc is used to simulate HTTP requests.
 *  
 *  The UserRepository is mocked to isolate these tests from the database,
 * to control the repository's behavior in each scenario.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class UserLoginTest {
    private static final String VALID_EMAIL = "prueba@example.com";
    private static final String VALID_PASSWORD = "ContraseñaValida1";
    private static final String INVALID_PASSWORD = "ContraseñaInvalida1";
    private static final String NON_EXISTENT_EMAIL = "emailIncorrecto@example.com";

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MockMvc mockMvc;
    
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User(
            "Prueba1",
            "prueba1",
            VALID_EMAIL,
            passwordEncoder.encode(VALID_PASSWORD),
            Role.USER,
            LocalDate.parse("2005-05-05")
        );
    }

    private ResultActions performLogin(String email, String password) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.User.LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        """
                        {
                            "email": "%s",
                            "password": "%s"
                        } 
                        """, email, password)
                )
        );
    }

    @Test
    void loginUserTest() throws Exception {
        when(userRepository.findByEmail("prueba@example.com")).thenReturn(Optional.of(user));

        performLogin(VALID_EMAIL, VALID_PASSWORD)
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void incorrectPasswordLoginTest() throws Exception {
        when(userRepository.findByEmail("prueba@example.com")).thenReturn(Optional.of(user));
        performLogin(VALID_EMAIL, INVALID_PASSWORD)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void inexistentEmailLoginTest() throws Exception {
        when(userRepository.findByEmail("emailIncorrecto@example.com")).thenReturn(Optional.empty());
        performLogin(NON_EXISTENT_EMAIL, INVALID_PASSWORD)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
