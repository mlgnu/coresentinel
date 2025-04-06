package com.districtcore.coresentinel.util;

public class StringUtils {
    public static String truncateStringPreserveWord(String str, int max) {
        if (max <= 0 || str == null || str.length() <= max) {
            return str;
        }

        if (str.charAt(max) == ' ') {
            return str.substring(0, max + 1) + "...";
        }

        int spaceIndex = str.substring(max).indexOf(" ");

        if (spaceIndex != -1) {
            int newMax = max + spaceIndex;
            return str.substring(0, newMax) + "...";
        } else {
            return str.substring(0, max) + "...";
        }
    }
}
