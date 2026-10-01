package org.rmagallangonzalez.hotel_reservation_system.user.dto;

import java.time.LocalDate;

import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private User.Role role;
    @NotNull(message = "Date of birth cannot be null")
    //@NotBlank(message = "Date of birth cannot be blank")
    private LocalDate dateOfBirth;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(
            this.firstName, this.lastName, this.email,
            passwordEncoder.encode(this.password), this.role,
            this.dateOfBirth
        );

        return user;
    }
}
