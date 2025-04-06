package com.districtcore.coresentinel.dto.person;

import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetPersonsDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetPersonsResponseDto {
        String name;
        Integer age;
        @JsonInclude(Include.NON_NULL)
        String role;
        PersonType type;
        Gender gender;
    }
}
