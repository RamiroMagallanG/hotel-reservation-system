package org.rmagallangonzalez.hotel_reservation_system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogedResponseDTO {
    private String bearerToken;
}
