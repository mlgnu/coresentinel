package com.districtcore.coresentinel.dto.crimecase;

import com.districtcore.coresentinel.dto.person.GetUserDto.UserIdNameRoleResponseDto;
import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

public class GetAllCasesDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetAllCasesResponseDto {
        private String caseNumber;
        private String caseName;
        private String description;
        private String area;
        private UserIdNameRoleResponseDto createdBy;
        private LocalDateTime createdAt;
        private String caseType;
        private CriticalityLevel level;
        private Set<CrimeCaseReportsResponseDto> reportedBy;
        private Integer numberOfAssignees;
        private Long numberOfEvidence;
        private Long numberOfSuspects;
        private Long numberOfVictims;
        private Long numberOfWitnesses;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CrimeCaseReportsResponseDto {
        private String reportId;
        private String email;
        private String civil_id;
        private String name;
    }
}
