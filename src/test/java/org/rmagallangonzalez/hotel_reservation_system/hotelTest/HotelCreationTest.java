package org.rmagallangonzalez.hotel_reservation_system.hotelTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.common.JwtUtil;
import org.rmagallangonzalez.hotel_reservation_system.hotel.Hotel;
import org.rmagallangonzalez.hotel_reservation_system.hotel.HotelRepository;
import org.rmagallangonzalez.hotel_reservation_system.hotel.HotelStaff;
import org.rmagallangonzalez.hotel_reservation_system.hotel.HotelStaffRepository;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Integration test for the endpoint to create hotels
 * @SpringBootTest is used to set up the Spring context, and
 * @AutoConfigureMockMvc is used to simulate HTTP requests.
 * 
 * HotelRepository and UserRepository are mocked to isolate
 * them from the database and control their behaviou
 * 
 * 
 */
@SpringBootTest 
@AutoConfigureMockMvc 
@Import(SecurityConfig.class)
public class HotelCreationTest {
    // ### Hotel ###
    private static final String HOTEL_NAME = "Five stars Hotel";
    private static final String HOTEL_ADDRESS = "Fake street, 123";

    // ### admin ###
    private static final String ADMIN_FIRST_NAME = "adminFirstName";
    private static final String ADMIN_LAST_NAME = "adminLastName";
    private static final String ADMIN_EMAIL = "adming@example.com";
    private static final String ADMIN_PASSWORD = "adminPassword";
    private static final LocalDate ADMIN_DATE_OF_BIRTH = LocalDate.of(2026, 10, 6);

    // ### manager ###
    private static final String MANAGER_FIRST_NAME = "managerFirstName";
    private static final String MANAGER_LAST_NAME = "managerLastName";
    private static final String MANAGER_EMAIL = "manager@example.com";
    private static final String MANAGER_PASSWORD = "managerPassword";
    private static final LocalDate MANAGER_DATE_OF_BIRTH = LocalDate.of(2026, 10, 6);

    // ### client ###
    private static final String CLIENT_FIRST_NAME = "clientFirstName";
    private static final String CLIENT_LAST_NAME = "clientLastName";
    private static final String CLIENT_EMAIL = "client@example.com";
    private static final String CLIENT_PASSWORD = "clientPassword";
    private static final LocalDate CLIENT_DATE_OF_BIRTH = LocalDate.of(2026, 10, 6);

    @MockitoBean 
    private HotelRepository hotelRepository;
    @MockitoBean 
    private UserRepository userRepository;
    @MockitoBean 
    private HotelStaffRepository hotelStaffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired 
    private JwtUtil jwtUtil;

    private User manager;
    private User admin;
    private User client;

    private String createBearer(User user) {
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(
            user.getEmail(), null, user.getAuthorities()
        );

        return "Bearer " + this.jwtUtil.createToken(auth);
    }

    private ResultActions creationRequest(User user) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
            .post(ApiRoutes.Hotel.CREATE_HOTEL)
            .header(HttpHeaders.AUTHORIZATION, this.createBearer(user))
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format(
                """
                {
                    "name": "%s",
                    "address": "%s"
                }
                """
                , HOTEL_NAME, HOTEL_ADDRESS)
            )
        );
    }

    private ResultActions creationRequest() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
            .post(ApiRoutes.Hotel.CREATE_HOTEL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format(
                """
                {
                    "name": "%s",
                    "address": "%s"
                }
                """
                , HOTEL_NAME, HOTEL_ADDRESS)
            )
        );
    }

    @BeforeEach
    void setUp() {
        this.manager = new User(
            MANAGER_FIRST_NAME, MANAGER_LAST_NAME, MANAGER_EMAIL,
            this.passwordEncoder.encode(MANAGER_PASSWORD),
            UserRole.STAFF, MANAGER_DATE_OF_BIRTH
        );

        this.admin = new User(
            ADMIN_FIRST_NAME, ADMIN_LAST_NAME, ADMIN_EMAIL,
            passwordEncoder.encode(ADMIN_PASSWORD),
            UserRole.ADMIN, ADMIN_DATE_OF_BIRTH
        );

        this.client = new User(
            CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_EMAIL,
            passwordEncoder.encode(CLIENT_PASSWORD),
            UserRole.CLIENT, CLIENT_DATE_OF_BIRTH
        );
    }

    @Test
    void createHotelWithAdmin() throws Exception {
        when(this.userRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.of(this.admin));
        when(this.hotelRepository.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(this.hotelStaffRepository.save(any(HotelStaff.class))).thenAnswer(invocation -> invocation.getArgument(0));

        creationRequest(this.admin)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(HOTEL_ADDRESS))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(HOTEL_NAME));
    }

    @Test
    void createHotelWithManager() throws Exception {
        when(this.userRepository.findByEmail(MANAGER_EMAIL)).thenReturn(Optional.of(this.manager));
        when(this.hotelRepository.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        creationRequest(this.manager)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(HOTEL_ADDRESS))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(HOTEL_NAME));
    }

    @Test
    void createHotelWithoutBeingLogged() throws Exception {
        creationRequest()
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void createHotelWithIncorrectRol() throws Exception {
        when(this.userRepository.findByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(this.client));

        this.creationRequest(this.client)
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
