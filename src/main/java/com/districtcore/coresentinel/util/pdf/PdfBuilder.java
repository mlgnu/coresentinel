package com.districtcore.coresentinel.util.pdf;

import com.districtcore.coresentinel.util.pdf.TableOfContentBuilder.TocEntry;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Getter
public class PdfBuilder {
    private final Document document;
    private final ByteArrayOutputStream outputStream;
    private final PdfDocument pdf;
    private final TableOfContentBuilder tocBuilder;
    private int section = 0;
    private int subsection = 0;

    public PdfBuilder() throws IOException {
        this.tocBuilder = new TableOfContentBuilder(new ArrayList<TocEntry>());
        outputStream = new ByteArrayOutputStream();
        pdf = new PdfDocument(new PdfWriter(outputStream));
        document = new Document(pdf, PageSize.A4);
        document.setTopMargin(40);
        document.setBottomMargin(40);
    }

    public PdfBuilder addTitle(String text) {
        Paragraph title = new Paragraph(text)
                .setFontSize(20)
                .setFont(PdfStyles.BOLD_FONT)
                .setMarginBottom(70)
                .setMarginTop(150)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        document.add(title);
        return this;
    }


    public PdfBuilder addStartPageEvent(AbstractPdfDocumentEventHandler handler) {
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
        return this;
    }

    public PdfBuilder removeEventHandler(AbstractPdfDocumentEventHandler handler) {
        pdf.removeEventHandler(handler);
        return this;
    }

    public PdfBuilder addParagraph(String text, PdfFont font, TextAlignment alignment) {
        Paragraph paragraph = new Paragraph(text).setFont(font).setTextAlignment(alignment);
        document.add(paragraph);
        return this;
    }

    public PdfBuilder addParagraph(String text, PdfFont font) {
       Paragraph paragraph = new Paragraph(text).setFont(font);
       document.add(paragraph);
       return this;
    }

    public PdfBuilder addNormalParagraph(String text) {
        Paragraph paragraph = new Paragraph(text).setFont(PdfStyles.NORMAL_FONT);
        document.add(paragraph);
        return this;
    }


    public PdfBuilder addTable(TableBuilder table) {
        document.add(table.build());
        return this;
    }

    public PdfBuilder addSectionTitle(String text) {
        subsection = 0;
        section += 1;
        String title = section + ". " + text;
        Paragraph paragraph = new Paragraph(title).setFont(PdfStyles.BOLD_FONT)
                        .setFontSize(17);
        addNewPage().getDocument().add(paragraph);
        tocBuilder.addEntry(title, title, true, pdf.getPageNumber(pdf.getLastPage()));
        return this;
    }

    public PdfBuilder addSubsectionTitle(String text) {
        subsection += 1;

        String title = section + ". " + subsection + " " + text;
        Paragraph paragraph = new Paragraph(title).setFont(PdfStyles.BOLD_FONT)
                        .setFontSize(15).setFontColor(ColorConstants.GRAY).setMarginTop(10);
        document.add(paragraph);
        tocBuilder.addEntry(title, title, false, pdf.getPageNumber(pdf.getLastPage()));
        return this;
    }

    public PdfBuilder addNewPage() {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        return this;
    }

    public PdfBuilder addImage(byte[] imageBytes) {
        Image image = new Image(ImageDataFactory.create(imageBytes))
                .setAutoScale(true);
        document.add(image);
        return this;
    }

    public byte[] build() {

        int tocStartPage = pdf.getPageNumber(pdf.getLastPage()) + 1;
        document.add(tocBuilder.build(tocStartPage));
        int tocEndPage = pdf.getPageNumber(pdf.getLastPage());
        tocBuilder.repositionToc(document, tocEndPage);
        document.close();
        return outputStream.toByteArray();
    }
}
