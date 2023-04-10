/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.embed.swing.SwingFXUtils;

/**
 *
 * @author fptshop.com.vn
 */
public class ExportPDF {

    public ExportPDF() {
    }

    public static void convertFXMLToPDF(InputStream fxmlInputStream, String outputPdfFilePath) throws IOException {
        // Load FXML file using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fxmlInputStream);

        // Render the FXML to an image using SnapshotParameters and WritableImage
        SnapshotParameters params = new SnapshotParameters();
        params.setDepthBuffer(true);
        params.setFill(Color.WHITE);
        WritableImage snapshot = root.snapshot(params, null);
        WritableImage writableImage = snapshot;
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try ( // Create a PDF document with a single page
                PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle((float) snapshot.getWidth(), (float) snapshot.getHeight()));
            document.addPage(page);
            // Convert the rendered image to a PDImageXObject and draw it on the PDF page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                contentStream.drawImage(pdImage, 0, 0);
            }
            // Save the PDF document to the output file path
            document.save(outputPdfFilePath);
        }
    }

}
