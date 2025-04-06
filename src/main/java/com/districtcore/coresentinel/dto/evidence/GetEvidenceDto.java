package com.districtcore.coresentinel.dto.evidence;

import com.districtcore.coresentinel.enums.EvidenceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetEvidenceDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetEvidenceResponseDto {
        private String id;
        private String remarks;
        private String resource;
        private EvidenceType type;

        @JsonInclude(Include.NON_NULL)
        private String imageSize;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageEvidence {
        private String contentType;
        private byte[] image;
    }
}
