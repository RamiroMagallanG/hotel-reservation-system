package org.rmagallangonzalez.hotel_reservation_system.user;

import org.rmagallangonzalez.hotel_reservation_system.common.ApiRoutes;
import org.rmagallangonzalez.hotel_reservation_system.common.JwtUtil;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserLoggedResponseDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserLoginDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserRegistrationDTO;
import org.rmagallangonzalez.hotel_reservation_system.user.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(ApiRoutes.User.BASE)
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(
        UserService userService, AuthenticationManager authenticationManager,
        JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(ApiRoutes.User.REGISTER)
    public ResponseEntity<UserResponseDTO> registerNewUser(@Valid @RequestBody UserRegistrationDTO dto) {
        User savedUser = this.userService.registerNewUser(dto);

        return new ResponseEntity<UserResponseDTO>(
            new UserResponseDTO(savedUser),
            HttpStatus.CREATED
        );
    }

    @PostMapping(ApiRoutes.User.LOGIN)
    public ResponseEntity<UserLoggedResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO dto) {
        Authentication authenticationRequest = 
            UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(), dto.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationRequest);
        
        return ResponseEntity.ok(new UserLoggedResponseDTO(this.jwtUtil.createToken(authentication)));
    }
}
