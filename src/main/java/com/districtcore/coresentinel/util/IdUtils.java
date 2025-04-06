package com.districtcore.coresentinel.util;

import java.util.List;

public class IdUtils {
    public static List<Long> unprefixIds(List<String> ids) {
        return ids.stream().map(id -> id.substring(1)).map(Long::parseLong).toList();
    }
}
