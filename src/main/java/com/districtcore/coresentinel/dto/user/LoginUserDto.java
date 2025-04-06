package com.districtcore.coresentinel.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginUserDto {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginUserRequestDto {
        private String username;
        private String password;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginUserResponseDto {
        private String token;
    }
}
