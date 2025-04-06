package com.districtcore.coresentinel.util.pdf;

import com.districtcore.coresentinel.enums.CaseStatus;
import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.enums.Gender;
import com.districtcore.coresentinel.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Report {
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportMetaData {
        private String caseId;
        private String generatedBy;
        private Double version;
        private String generatedOn;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportCaseDetails {
        private String name;
        private String description;
        private CaseStatus status;
        private String date;
        private CriticalityLevel level;
        private String city;
        private String area;
        private String creator;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportTextEvidence {
        private String evidence;
        private String remarks;
        private String uploadedAt;
        private String uploadedBy;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportImageEvidence {
        private byte[] evidence;
        private String remarks;
        private String uploadedAt;
        private String uploadedBy;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportPersons {
        private Integer age;
        private Gender gender;
        private String name;
        private PersonType type;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportOfficer {
        private String name;
        private String email;
        private CriticalityLevel level;
    }

}
