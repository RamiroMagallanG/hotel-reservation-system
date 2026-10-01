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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class UserLoginTest {
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
            "prueba@example.com",
            passwordEncoder.encode("123456"),
            Role.USER,
            LocalDate.parse("2005-05-05")
        );
    }

    @Test
    void loginUserTest() throws Exception {
        when(userRepository.findByEmail("prueba@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.LOGIN_URL)
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "prueba@example.com",
                        "password": "123456"
                    }        
                    """
                )
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void incorrectPasswordLoginTest() throws Exception {
        when(userRepository.findByEmail("prueba@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.LOGIN_URL)
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "prueba@example.com",
                        "password": "1234567"
                    }        
                    """
                )
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    void inexistentEmailLoginTest() throws Exception {
        when(userRepository.findByEmail("emailIncorrecto@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.LOGIN_URL)
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "emailIncorrecto@example.com",
                        "password": "1234567"
                    }        
                    """
                )
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest()
        );
    }
}
