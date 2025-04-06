package com.districtcore.coresentinel.util.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TabAlignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class TableOfContentBuilder {

    private final List<TocEntry> tocEntries;
    private int startPage;
    private int endPage;

    public TableOfContentBuilder(List<TocEntry> tocEntries) {
        this.tocEntries = tocEntries;
    }

    public void addEntry(String title, String destination, boolean isMainEntry, int pageNumber) {
        tocEntries.add(new TocEntry(title, destination, isMainEntry, pageNumber));
    }

    private Paragraph formatTitle(String title) {
        return new Paragraph(title).setFont(PdfStyles.BOLD_FONT)
        .setFontSize(17);
    }

    private Paragraph formatTocEntry(TocEntry entry) {
        entry.setPageNumber(entry.getPageNumber() + 1);
        return entry.isMainEntry() ? formatTocMainEntry(entry) : formatTocSubEntry(entry);
    }

    private Paragraph formatTocMainEntry(TocEntry entry) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(entry.getTitle() + " ")
            .addTabStops(new TabStop(520, TabAlignment.RIGHT, new DottedLine(1.2f, 5f)))
            .add(new Tab())
            .add(" " + entry.getPageNumber())
            .setFont(PdfStyles.NORMAL_FONT)
            .setFontSize(11.5f);
        return paragraph;
    }


    private Paragraph formatTocSubEntry(TocEntry entry) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(entry.getTitle() + " ")
                .setMarginLeft(1)
                .addTabStops(new TabStop(510, TabAlignment.RIGHT, new DottedLine(0.9f, 5f)))
                .add(new Tab())
                .add(" " + entry.getPageNumber())
                .setFont(PdfStyles.NORMAL_FONT)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginLeft(10f)
                .setFontSize(10f);
        return paragraph;
    }

    public IBlockElement repositionToc(Document document, int endPage) {
        PdfDocument pdfDocument = document.getPdfDocument();
        this.endPage = endPage;

        // handles the case where TOC is more than one page
        for (int p = endPage; p >= startPage; p--) {
            document.getPdfDocument().movePage(p, 2);
        }

        return recalculateTocPages(endPage - startPage);
    }

    private IBlockElement recalculateTocPages(int shiftedBy) {
        for (TocEntry entry : tocEntries) {
            if (entry.getPageNumber() > startPage) {
                entry.setPageNumber(entry.getPageNumber() + shiftedBy);
            }
        }
        return generateToc();
    }

    private IBlockElement generateToc() {
        Div div = new Div();

        div.add(new AreaBreak(AreaBreakType.NEXT_PAGE))
                .add(formatTitle(" Table of Contents"));
        tocEntries.stream().map(this::formatTocEntry).forEach(div::add);
        return div;
    }

    public IBlockElement build(int startPage) {
        this.startPage = startPage;
        return generateToc();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TocEntry {
        private String title;
        private String destination;
        private boolean isMainEntry;
        private int pageNumber;

        public TocEntry(String title, String destination, boolean isMainEntry) {
            this.title = title;
            this.destination = destination;
            this.isMainEntry = isMainEntry;
        }
    }
}
