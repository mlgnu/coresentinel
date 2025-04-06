package com.districtcore.coresentinel.dto.user;

import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class UpdateUserDto {
    @Data
    @AllArgsConstructor
    public static class UpdateUserRequestDto {
            @Size(min = 6, max = 20)
            final String password;

            @Email(message = "Email should be valid")
            final String email;

            @Size(min = 3, max = 50, message = "Full name should be between 3 and 50 characters")
            final String fullName;

            final UserRole role;
    }

    @Data
    public static class UpdateUserRolesRequestDto {
        @NotNull(message = "Role cannot be blank")
        final UserRole role;
    }

    @Data
    public static class UpdateUserLevelRequestDto {
        @NotNull(message = "Criticality level cannot be blank")
        final CriticalityLevel criticalityLevel;
    }
}