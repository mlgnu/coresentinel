package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

//@Schema(allowableValues = {"FIRST_VALUE", "SECOND_VALUE"})

public enum UserRole {
    ADMIN,
    INVESTIGATOR,
    OFFICER;

    @JsonCreator
    public static UserRole fromString(String value) {
        return valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
}

