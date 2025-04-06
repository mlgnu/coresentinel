package com.districtcore.coresentinel.dto.crimereport;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class ReportCrimeDto {
    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReportCrimeRequest {
        @NotEmpty(message = "Title is required")
        private String description;

        @NotEmpty(message = "Location is required")
        private String location;

        @NotNull(message = "Date and time is required")
//        @Schema(type = "string", format = "date-time", pattern = "yyyy-MM-dd HH:mm:ss", example = "2025-03-23 06:14:04")
        private LocalDateTime dateAndTime;

        @NotNull(message = "Reporter details is required")
        @Valid
        private ReporterDetails reporterDetails;
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReporterDetails {
        @NotEmpty(message = "ID is required")
        @Length(min = 5, max = 20, message = "ID should be between 5 and 20 characters")
        private String id;

        @NotEmpty(message = "Name is required")
        private String name;

        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReportCrimeResponse {
        private String reportId;
    }
}

