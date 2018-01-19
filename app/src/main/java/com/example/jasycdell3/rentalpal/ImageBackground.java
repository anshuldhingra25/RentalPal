package com.example.jasycdell3.rentalpal;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by JASYC on 16-09-2016.
 */
public class ImageBackground implements PdfPCellEvent {
    protected Image image;
    public ImageBackground(Image image) {
        this.image = image;
    }
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];
        image.scaleToFit(position.getWidth() , position.getHeight());
        image.setAbsolutePosition(position.getBottom(), position.getLeft());
        try {
            cb.addImage(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
