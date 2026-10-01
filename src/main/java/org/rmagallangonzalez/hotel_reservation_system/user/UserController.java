package org.rmagallangonzalez.hotel_reservation_system.user;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.common.JwtUtil;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserLogedResponseDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserLoginDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserRegistrationDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(ApiRoutes.USER_URL)
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(
        UserService userService, PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager, JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(ApiRoutes.REGISTER)
    public ResponseEntity<?> registerNewUser(
        @Valid @RequestBody UserRegistrationDTO userRegistrationDTO
    ) {
        userRegistrationDTO.setRole(User.Role.USER);

        return new ResponseEntity<UserResponseDTO>(
            new UserResponseDTO(this.userService.save(userRegistrationDTO.toUser(passwordEncoder))),
            HttpStatus.CREATED
        );
    }

    @PostMapping(ApiRoutes.LOGIN)
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        Authentication authenticationRequest = 
            UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationRequest);
        
        return new ResponseEntity<UserLogedResponseDTO>(
            new UserLogedResponseDTO(this.jwtUtil.createToken(authentication)),
            HttpStatus.OK
        );
    }
}
