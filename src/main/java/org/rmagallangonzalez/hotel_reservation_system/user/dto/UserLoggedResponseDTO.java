package org.rmagallangonzalez.hotel_reservation_system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoggedResponseDTO {
    private String bearerToken;
}
