package com.districtcore.coresentinel.dto.evidence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HardDeleteEvidenceDto {
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HardDeleteEvidenceConfirmResponseDto {
        private String token;
    }
}
