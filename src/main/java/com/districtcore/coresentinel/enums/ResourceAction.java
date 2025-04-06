package com.districtcore.coresentinel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceAction {
    CREATE, UPDATE, DELETE, SOFT_DELETE;


    @JsonCreator
    public ResourceAction fromString(String value) {
        return valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
}
