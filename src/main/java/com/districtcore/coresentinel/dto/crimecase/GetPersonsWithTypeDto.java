package com.districtcore.coresentinel.dto.crimecase;

import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetPersonsWithTypeDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetPersonsWithTypeResponseDto {
        private String personId;
        private String name;

        @JsonInclude(Include.NON_NULL)
        private PersonType type;

        @JsonInclude(Include.NON_NULL)
        private Gender gender;
    }
}
