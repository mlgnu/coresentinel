package com.districtcore.coresentinel.util.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class HeaderFooterEventHandler extends AbstractPdfDocumentEventHandler {
    @Override
    protected void onAcceptedEvent(AbstractPdfDocumentEvent abstractPdfDocumentEvent) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) abstractPdfDocumentEvent;
        PdfDocument pdfDoc = docEvent.getDocument();
        Rectangle pageSize = docEvent.getPage().getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(docEvent.getPage());
        int pageNumber = pdfDoc.getPageNumber(docEvent.getPage());

        if (pageNumber <= 1) {
            return;
        }

        Canvas canvas = new Canvas(pdfCanvas, pageSize);
        Paragraph header = new Paragraph("CoreSentinel Crime Management Report")
                .setFont(PdfStyles.NORMAL_FONT)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT);
        Paragraph slogan = new Paragraph("Restoring trust, Enhancing Security")
                .setFont(PdfStyles.NORMAL_FONT)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.GRAY);
        canvas.showTextAligned(header,
                pageSize.getLeft() + 10,
                pageSize.getTop() - 20,
                TextAlignment.LEFT);
        canvas.showTextAligned(slogan,
                pageSize.getLeft() + 10,
                pageSize.getTop() - 33,
                TextAlignment.LEFT);

        Paragraph pageNum = new Paragraph(Integer.toString(pageNumber + 1))
                .setFont(PdfStyles.NORMAL_FONT)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontColor(ColorConstants.GRAY);
        Rectangle rect = new Rectangle(pageSize.getLeft() + 10, pageSize.getBottom() + 10, 550, 35);
        Canvas disclaimerCanvas = new Canvas(pdfCanvas, rect);

        Paragraph disclaimer = new Paragraph("This document contains confidential information intended solely for authorized personnel" +
                " of CoreSentinel. Unauthorized access, use, disclosure, or distribution is strictly prohibited. " +
                "If you have received this document in error, please notify the sender immediately and delete all copies.")
                .setFont(PdfStyles.NORMAL_FONT)
                .setFontSize(8.7f)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.GRAY);
        canvas.showTextAligned(pageNum,
                pageSize.getWidth() - 20,
                pageSize.getBottom() + 20,
                TextAlignment.RIGHT);
        disclaimerCanvas.add(disclaimer);

//        canvas.showTextAligned(new Paragraph("CONFIDENTIAL").setFont(PdfStyles.BOLD_FONT).setFontSize(50).setFontColor(ColorConstants.BLUE, 0.7f).setWidth(300),
//                298, 421, pdfDoc.getPageNumber(docEvent.getPage()),
//                TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        pdfCanvas.release();
        disclaimerCanvas.close();
        canvas.close();
    }
}
