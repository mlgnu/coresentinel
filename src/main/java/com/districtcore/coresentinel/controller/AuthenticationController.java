package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.dto.user.LoginUserDto;
import com.districtcore.coresentinel.dto.user.LoginUserDto.LoginUserResponseDto;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.service.AuthenticationService;
import com.districtcore.coresentinel.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication for registered users")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticate a registered user and return a JWT token")
    public ResponseEntity<LoginUserResponseDto> authenticate(@RequestBody @Valid LoginUserDto.LoginUserRequestDto loginUserRequestDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserRequestDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginUserResponseDto loginResponse = new LoginUserResponseDto(jwtToken);
        return ResponseEntity.ok(loginResponse);
    }
}
