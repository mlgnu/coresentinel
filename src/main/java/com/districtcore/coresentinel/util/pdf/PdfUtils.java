package com.districtcore.coresentinel.util.pdf;

public class PdfUtils {
    public static PdfBuilder createPdfBuilder() throws Exception {
        return new PdfBuilder();
    }

    public static TableBuilder createTableBuilder(float[] columnSizes) {
        return new TableBuilder(columnSizes);
    }
}
