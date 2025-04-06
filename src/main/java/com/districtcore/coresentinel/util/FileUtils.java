package com.districtcore.coresentinel.util;

import org.springframework.web.multipart.MultipartFile;
import java.text.DecimalFormat;

public class FileUtils {

    public static String getFormattedFileSize(MultipartFile file) {
        long sizeInBytes = file.getSize();
        return formatSize(sizeInBytes);
    }

    public static String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        double sizeInUnit = size / Math.pow(1024, digitGroups);
        return new DecimalFormat("#,##0.#").format(sizeInUnit) + " " + units[digitGroups];
    }
}
