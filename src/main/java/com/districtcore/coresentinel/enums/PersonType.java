package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PersonType {

    VICTIM, SUSPECT, WITNESS;

    @JsonCreator
    public PersonType fromString(String value) {
        return valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
