package com.example.jasycdell3.rentalpal;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by JASYC on 16-09-2016.
 */
public class PDFInt extends PdfPageEventHelper {
    protected PdfPTable footer;

    public PDFInt(PdfPTable footer) {
        this.footer = footer;
    }

    public void onEndPage(PdfWriter writer, Document document) {
        footer.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());
    }
}