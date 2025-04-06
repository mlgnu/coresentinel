package com.districtcore.coresentinel.dto.crimecase;

import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.model.CrimeReport;
import com.districtcore.coresentinel.model.Person;
import com.districtcore.coresentinel.model.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CreateCaseDto {
    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @AllArgsConstructor
    @NoArgsConstructor()
    public static class CreateCaseRequestDto {
        @NotEmpty(message = "Case name cannot be empty")
        private String caseName;

        @NotEmpty(message = "Case description cannot be empty")
        private String description;

        @NotEmpty(message = "Case area cannot be empty")
        private String area;

        @NotEmpty(message = "Case city cannot be empty")
        private String city;

        @NotEmpty(message = "Case type cannot be empty")
        private String caseType;

        @NotNull(message = "Case level cannot be empty")
        private CriticalityLevel level;

        @NotNull(message = "Assignees cannot be empty")
        @ValidEntityIds(entityClass = User.class)
        private List<String> assigneesId;

        @NotNull(message = "Reports cannot be empty")
        @ValidEntityIds(entityClass = CrimeReport.class)
        private List<String> reportsId;

        @ValidEntityIds(entityClass =  Person.class)
        private List<String> personsId;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCaseResponseDto {
        private final String caseId;
    }
}
