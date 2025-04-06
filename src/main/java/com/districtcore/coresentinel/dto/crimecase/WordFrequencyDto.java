package com.districtcore.coresentinel.dto.crimecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WordFrequencyDto {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordFrequencyResponseDto {
        private String word;
        private Integer frequency;
    }
}
