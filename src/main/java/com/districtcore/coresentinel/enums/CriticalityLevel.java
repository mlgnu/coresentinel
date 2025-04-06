package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CriticalityLevel {
    LOW(0),
    MEDIUM(1),
    HIGH(2),
    CRITICAL(3);

    private final int code;

    CriticalityLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CriticalityLevel fromCode(int code) {
        for (CriticalityLevel level : values()) {
            if (level.getCode() == code) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    @JsonCreator
    public static CriticalityLevel fromString(String value) {
        return valueOf(value.toUpperCase());
    }
    @Override
    @JsonValue
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
}