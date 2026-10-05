package org.rmagallangonzalez.hotel_reservation_system.hotelTest;

import org.rmagallangonzalez.hotel_reservation_system.hotel.HotelRepository;
import org.rmagallangonzalez.hotel_reservation_system.security.SecurityConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest 
@AutoConfigureMockMvc 
@Import(SecurityConfig.class)
public class HotelCreationTest {
    @MockitoBean 
    private HotelRepository hotelRepository;
    

}
