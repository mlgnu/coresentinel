package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CaseStatus {
    PENDING, ONGOING, CLOSED;

    @JsonCreator
    public static CaseStatus fromString(String value) {
        return valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
}
