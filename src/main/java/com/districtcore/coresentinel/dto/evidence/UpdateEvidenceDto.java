package com.districtcore.coresentinel.dto.evidence;

import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.annotation.ValidImage;
import com.districtcore.coresentinel.model.CrimeCase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class UpdateEvidenceDto {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateEvidenceRequestDto {
        private String remarks;

        @ValidImage
        @Schema(description = "Image file (optional) - max size is 2MB", type = "string", format = "binary")
        private MultipartFile image;

        private String text;

        @ValidEntityIds(entityClass = CrimeCase.class)
        private String caseId;

        @Schema(
            description = "ID with prefix (e.g., 'A123', 'R456')",
            type = "string",
            pattern = "^[A-Za-z]\\d+$",
            example = "E123"
        )
        @PrefixedId(prefix = "E")
        private String id;


        @JsonIgnore
        @AssertTrue(message = "Either an image or text must be provided, but not both")
        public boolean isResourceValid() {
            boolean isImageProvided = (image != null && !image.isEmpty());
            boolean isTextProvided = (text != null && !text.isEmpty());
            return (isImageProvided || isTextProvided) && !(isImageProvided && isTextProvided);
        }

    }
}
