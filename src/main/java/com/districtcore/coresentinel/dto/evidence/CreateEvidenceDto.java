package com.districtcore.coresentinel.dto.evidence;

import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.annotation.ValidImage;
import com.districtcore.coresentinel.model.CrimeCase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class CreateEvidenceDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateEvidenceRequestDto {
        private String remarks;

        @ValidImage
        @Schema(description = "Image file (optional) - max size is 2MB", type = "string", format = "binary")
        private MultipartFile image;

        private String text;

        @ValidEntityIds(entityClass = CrimeCase.class)
        @NotEmpty(message = "Case Id cannot be empty")
        private String caseId;

        @JsonIgnore
        @AssertTrue(message = "Either an image or text must be provided, but not both")
        public boolean isResourceValid() {
            boolean isImageProvided = (image != null && !image.isEmpty());
            boolean isTextProvided = (text != null && !text.isEmpty());
            return (isImageProvided || isTextProvided) && !(isImageProvided && isTextProvided);
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateEvidenceResponseDto {
        private String evidenceId;
    }
}
