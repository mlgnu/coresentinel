package com.districtcore.coresentinel.util;

import com.districtcore.coresentinel.model.*;

import java.util.HashMap;
import java.util.Map;

public class EntityPrefixUtils {
    private static final Map<Class<?>, Character> ENTITY_PREFIX_MAP = new HashMap<>();

    static {
        ENTITY_PREFIX_MAP.put(User.class, 'A');
        ENTITY_PREFIX_MAP.put(CrimeReport.class, 'R');
        ENTITY_PREFIX_MAP.put(CrimeCase.class, 'C');
        ENTITY_PREFIX_MAP.put(Person.class, 'P');
        ENTITY_PREFIX_MAP.put(Comment.class, 'C');
    }

    public static String getPrefixedId(Class<?> entityClass, Long id) {
        Character prefix = ENTITY_PREFIX_MAP.get(entityClass);
        if (prefix == null) {
            throw new IllegalArgumentException("No prefix defined for entity: " + entityClass.getSimpleName());
        }
        return prefix + id.toString();
    }

    public static Long getIdFromPrefixedId(String prefixedId) {
        return Long.parseLong(prefixedId.substring(1));
    }

    public static Character getClassPrefix(Class<?> entityClass) {
        return ENTITY_PREFIX_MAP.get(entityClass);
    }
}
