package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE, FEMALE;

    @JsonCreator
    public Gender fromString(String value) {
        return valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }

}
