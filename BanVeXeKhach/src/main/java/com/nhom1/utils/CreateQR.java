/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 *
 * @author fptshop.com.vn
 */
public class CreateQR {

    public static Image generateQRCodeImage(String codeText) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeText, BarcodeFormat.QR_CODE, 200, 200);

        WritableImage writableImage = new WritableImage(200, 200);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                writableImage.getPixelWriter().setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        ImageView imageView = new ImageView(writableImage);
        Image img = imageView.getImage();
        return img;
    }
}
