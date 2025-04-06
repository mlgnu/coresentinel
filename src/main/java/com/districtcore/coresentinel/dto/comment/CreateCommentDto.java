package com.districtcore.coresentinel.dto.comment;

import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.model.CrimeCase;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreateCommentDto {
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCommentRequestDto {
        @NotEmpty(message = "Comment cannot be empty")
        @Size(min = 5, message = "Comment must be at least 5 characters long")
        @Size(max = 150, message = "Comment cannot exceed 150 characters")
        @Pattern.List({
            @Pattern(
                    regexp = "^[a-zA-Z0-9\\s.,!?;:'\"()-]+$",
                    message = "Comment contains invalid characters. Please use only letters, numbers, and basic punctuation."
            ),
            @Pattern(
                    regexp = "^(?!.*<[^>]+>).*$",
                    message = "HTML tags are not allowed in comments."
            )
        })
        private String comment;

        @ValidEntityIds(entityClass = CrimeCase.class)
        private String caseId;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCommentResponseDto {
        private String commentId;
    }
}
