package org.rmagallangonzalez.hotel_reservation_system.userTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

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

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class UserRegisterTest {
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerNewUser() throws Exception{
        User user = new User(
            "Prueba1",
            "prueba1",
            "prueba@example.com",
            "password",
            Role.USER,
            LocalDate.parse("2005-05-05")
        );

        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.REGISTER_URL)
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "prueba@example.com",
                        "password": "123456",
                        "firstName": "Prueba1",
                        "lastName": "prueba1",
                        "dateOfBirth": "2005-05-05"
                    }
                    """
                )
        )
        .andExpect(
            MockMvcResultMatchers.status().isCreated()
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.firstName").value("Prueba1")
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.lastName").value("prueba1")
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value("prueba@example.com")
        );

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("123456");
    }

    @Test
    void registerNewUserWithRepeatedEmail() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(
            MockMvcRequestBuilders
            .post(ApiRoutes.REGISTER_URL)
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "prueba@example.com",
                        "password": "123456",
                        "firstName": "Prueba1",
                        "lastName": "prueba1",
                        "dateOfBirth": "2005-05-05"
                    }
                    """
                )
        )
        .andExpect(
            MockMvcResultMatchers.status().isConflict()
        )
        .andExpect(
            MockMvcResultMatchers.content().string("Email 'prueba@example.com' already exists")
        );
    }
}
