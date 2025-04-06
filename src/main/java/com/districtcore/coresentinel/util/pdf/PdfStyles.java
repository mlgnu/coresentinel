package com.districtcore.coresentinel.util.pdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.io.IOException;

public class PdfStyles {

    public static final String DARK_BLUE = "#0A2342";
    public static final String WHITE = "#FFFFFF";
    public static final String ACCENT_GOLD = "#D4AF37";
    public static final String LIGHT_GREY = "#F2F2F2";


//    public static final Font BOLD_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
//    public static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 13, BaseColor.DARK_GRAY);

    public static final PdfFont NORMAL_FONT;

    static {
        try {
            NORMAL_FONT = PdfFontFactory.createFont("/font/lato/Lato-Regular.ttf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final PdfFont BOLD_FONT;

    static {
        try {
            BOLD_FONT = PdfFontFactory.createFont("/font/lato/Lato-Bold.ttf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final PdfFont SEMI_BOLD_FONT;

    static {
        try {
            SEMI_BOLD_FONT = PdfFontFactory.createFont("/font/lato/Lato-Black.ttf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
