package com.districtcore.coresentinel.dto.person;

import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.PersonType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreatePersonDto {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreatePersonRequestDto {

        @NotEmpty(message = "Person name cannot be empty")
        private String name;

        @Min(value = 10, message = "age must be at least 10")
        @Max(value = 100, message = "age must be at most 100")
        private Integer age;

        @NotNull(message = "Person gender cannot be empty")
        private Gender gender;

        @NotNull(message = "Person type cannot be empty")
        private PersonType type;

        @NotEmpty(message = "Person role cannot be empty")
        private String role;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreatePersonResponseDto {
        private String personId;
    }
}
