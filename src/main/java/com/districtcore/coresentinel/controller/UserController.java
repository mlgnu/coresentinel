package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.dto.person.GetUserDto.GetUserResponseDto;
import com.districtcore.coresentinel.dto.user.SignupUserDto;
import com.districtcore.coresentinel.dto.user.SignupUserDto.SignupUserResponseDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserLevelRequestDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserRequestDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserRolesRequestDto;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.service.AuthenticationService;
import com.districtcore.coresentinel.service.JwtService;
import com.districtcore.coresentinel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manage users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Register a new user", description = "Register a new user and return their details")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SignupUserResponseDto> register(@RequestBody @Valid SignupUserDto.SignupUserRequestDto signupUserDto) {
        User registeredUser = authenticationService.signup(signupUserDto);
        SignupUserResponseDto signupResponse = new SignupUserResponseDto(registeredUser.getUserId(),
                registeredUser.getUsername(), registeredUser.getFullName(), registeredUser.getEmail());

        return ResponseEntity.ok(signupResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user details by their ID")
    public ResponseEntity<GetUserResponseDto> getUserById(
            @PrefixedId(prefix = "A") Long id
    ) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user by ID", description = "Update user details by their ID")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> updateById(
            @RequestBody @Valid UpdateUserRequestDto userDto,
            @PrefixedId(prefix = "A") Long id
            ) {
        userService.updateById(userDto, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/roles")
    @Operation(summary = "Update user roles by ID", description = "Update user roles by their ID")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> updateRolesById(
            @PrefixedId(prefix = "A") Long id,
            @RequestBody@Valid UpdateUserRolesRequestDto userRoles
    ) {
        userService.updateRolesById(userRoles, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/levels")
    @Operation(summary = "Update user levels by ID", description = "Update user levels by their ID")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> updateLevelsByUsername(
            @PrefixedId("A") Long id,
            @RequestBody@Valid UpdateUserLevelRequestDto userLevel
    ) {
        userService.updateLevelById(userLevel, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Delete user by their ID")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> deleteById(
            @PrefixedId(prefix = "A") Long id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
