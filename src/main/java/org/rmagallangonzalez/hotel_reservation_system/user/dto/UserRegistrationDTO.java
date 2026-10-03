package org.rmagallangonzalez.hotel_reservation_system.user.dto;

import java.time.LocalDate;

import org.rmagallangonzalez.hotel_reservation_system.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "The email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 64, message = "The password must be between 8 and 64 characters long")
    private String password;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 64, message = "The first name cannot exceed 64 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 64, message = "The last name cannot exceed 64 characters")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "The date of birth must be in the past")
    private LocalDate dateOfBirth;

    public User toUser() {
        User user = new User(
            this.firstName, this.lastName, this.email,
            this.password, this.dateOfBirth
        );

        return user;
    }
}
