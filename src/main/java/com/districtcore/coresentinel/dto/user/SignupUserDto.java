package com.districtcore.coresentinel.dto.user;

import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.enums.UserRole;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

public class SignupUserDto {
    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SignupUserRequestDto {
            @NotBlank(message = "Username cannot be blank") final String username;

            @NotBlank(message = "Password cannot be blank")
            @Size(min = 6, max = 20)
            String password;

            @Email(message = "Email should be valid")
            @NotBlank(message = "Email cannot be blank")
            final String email;

            @NotBlank(message = "Full name cannot be blank")
            @Size(min = 3, max = 50, message = "Full name should be between 3 and 50 characters")
            final String fullName;

            @NotNull(message = "Role cannot be blank")
            final UserRole role;

            final CriticalityLevel level;

            // level is only required for officers, therefore, we validate the role field when creating a new officer
            @AssertTrue(message = "Officer must have a user level provided")
            public boolean validateOfficer() {
                return role != UserRole.OFFICER || level != null;
            }
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SignupUserResponseDto {
            String userId;
            String username;
            String email;
            String fullName;
    }
}