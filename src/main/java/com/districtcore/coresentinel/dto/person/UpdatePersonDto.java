package com.districtcore.coresentinel.dto.person;

import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdatePersonDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePersonRequestDto {
       private String name;
       private Integer age;
       private Gender gender;
       private UserRole role;
       private String type;
    }
}
