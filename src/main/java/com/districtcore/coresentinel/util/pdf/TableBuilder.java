package com.districtcore.coresentinel.util.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.Getter;

/**
 *  Builds a table using builder design pattern with fluent API
 *  and returns it to be embedded in the document using the main pdf builder class
 */

@Getter
public class TableBuilder {
    private final Table table;

    public TableBuilder(float[] columnWidths) {
        table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
    }

    public TableBuilder addTableWidth(float width) {
        table.setWidth(UnitValue.createPointValue(width))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        return this;
    }

    public TableBuilder addMainCenteredHeader(String text) {
        Cell header = new Cell(1, table.getNumberOfColumns())
                .add(new Paragraph(text))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(header);
        return this;
    }

    public TableBuilder addMainCenteredRow(String[] row) {
        if (row.length != table.getNumberOfColumns()) {
            throw new IllegalArgumentException("Row length must match the number of columns in the table");
        }

        for (String cell : row) {
            table.addCell(new Cell().add(new Paragraph(cell).setMargin(3).setMarginLeft(5))
                    .setTextAlignment(TextAlignment.LEFT));
        }
        return this;
    }

    public TableBuilder addHeaderRow(String[] row) {
        if (row.length != table.getNumberOfColumns()) {
            throw new IllegalArgumentException("Row length must match the number of columns in the table");
        }
        for (String cell : row) {
            table.addHeaderCell(new Cell().add(new Paragraph(cell).setMargin(3).setMarginLeft(5))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }
        return this;
    }

    public TableBuilder addRow(String[] row) {
        if (row.length != table.getNumberOfColumns()) {
            throw new IllegalArgumentException("Row length must match the number of columns in the table");
        }

        int rowIndex = table.getNumberOfRows();
        for (String cellText : row) {
            table.addCell(
                    new Cell()
                            .add(new Paragraph(cellText).setMargin(2).setMarginLeft(5))
                            .setBackgroundColor(rowIndex % 2 == 0 ? ColorConstants.WHITE : ColorConstants.LIGHT_GRAY)
                            .setTextAlignment(TextAlignment.LEFT)
            );
        }
        return this;
    }

    public TableBuilder addParagraphCell(String text) {
            table.addCell(
                    new Cell()
                            .add(new Paragraph(text).setMargin(2).setMarginLeft(5))
                            .setTextAlignment(TextAlignment.LEFT)
            );
        return this;
    }

    public TableBuilder addFullGrayRow(String text) {
            table.addCell(
                    new Cell(1, table.getNumberOfColumns())
                            .add(new Paragraph(text).setMargin(1.5f).setMarginLeft(5))
                            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                            .setTextAlignment(TextAlignment.LEFT)
            );
        return this;
    }

    public TableBuilder addImageWithOverlay(Document document, byte[] imageBytes, String overlay) {
        Image image = new Image(ImageDataFactory.create(imageBytes))
                .scaleToFit(450, 450)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        float imgWidth = image.getImageScaledWidth();
        float imgHeight = image.getImageScaledHeight();

        PdfFormXObject template = new PdfFormXObject(new Rectangle(imgWidth, imgHeight));
        PdfCanvas pdfCanvas = new PdfCanvas(template, document.getPdfDocument());
        Canvas canvas = new Canvas(pdfCanvas, new Rectangle(imgWidth, imgHeight));

        canvas.add(image);

        Paragraph overlayParagraph = new Paragraph(overlay)
                .setFontSize(12)
                .setFont(PdfStyles.BOLD_FONT)
                .setFontColor(ColorConstants.WHITE)
                .setBackgroundColor(new DeviceRgb(0, 0, 0))
                .setOpacity(0.5f)
                .setTextAlignment(TextAlignment.CENTER);

        float x = imgWidth / 2;
        float y = 10;

        canvas.showTextAligned(overlayParagraph, x, y, TextAlignment.CENTER);
        Image compositeImage = new Image(template);
        table.addCell(new Cell().add(compositeImage));

        return this;
    }

    public Table build() {
        return table;
    }

}