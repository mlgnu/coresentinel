package com.districtcore.coresentinel.dto.crimecase;

import com.districtcore.coresentinel.enums.CaseStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CrimeCaseStatusDto {
    @Data
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CrimeCaseStatusResponseDto {
        private CaseStatus status;
        private String statusDetails;
    }
}
