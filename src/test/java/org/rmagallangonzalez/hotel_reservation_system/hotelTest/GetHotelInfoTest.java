package org.rmagallangonzalez.hotel_reservation_system.hotelTest;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.hotel.Hotel;
import org.rmagallangonzalez.hotel_reservation_system.hotel.HotelRepository;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc 
@Import(SecurityConfig.class)
public class GetHotelInfoTest {
    private static final long HOTEL_ID = 1;
    private static final long INEXISTENT_HOTEL_ID = 555;
    private static final String HOTEL_NAME = "Five star hotel";
    private static final String HOTEL_ADDRESS = "Fake Street, 123";

    @MockitoBean 
    private HotelRepository hotelRepository;

    @Autowired 
    private MockMvc mockMvc;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        this.hotel = new Hotel(HOTEL_NAME, HOTEL_ADDRESS);
    }

    private ResultActions sendRequest(long id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(ApiRoutes.Hotel.BASE + "/" + id));
    }

    @Test
    void getHotelInformationTest() throws Exception{
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(this.hotel));

        sendRequest(HOTEL_ID)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(HOTEL_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(HOTEL_ADDRESS));
    }

    @Test
    void tryGetInexistentHotelTest() throws Exception {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        sendRequest(INEXISTENT_HOTEL_ID)
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("The hotel with ID " + INEXISTENT_HOTEL_ID + " does not exist"));
    }
}
