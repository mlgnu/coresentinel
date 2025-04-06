package com.districtcore.coresentinel.dto.person;

import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.enums.UserRole;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

public class GetUserDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserResponseDto {
        String userId;
        String username;
        String email;
        String role;
        CriticalityLevel userLevel;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserIdNameRoleResponseDto {
        String userId;
        String name;
        UserRole role;
    }
}
