package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.ImageWatermarkDetails;
import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.exception.WatermarkDetailsException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class WatermarkDetailsRetriever {
    public ImageWatermarkDetails retrieve(ImageWatermarkRequest request) {
        byte[] image = request.image();
        byte[] watermark = request.watermark().image();

        try (
            InputStream imageStream = new ByteArrayInputStream(image);
            InputStream watermarkStream = new ByteArrayInputStream(watermark)
        ) {
            BufferedImage originalImage = ImageIO.read(imageStream);
            BufferedImage watermarkImage = ImageIO.read(watermarkStream);
            BufferedImage resultImage = resultImage(originalImage);
            return new ImageWatermarkDetails(originalImage, watermarkImage, resultImage);
        } catch (IOException exception) {
            throw new WatermarkDetailsException(exception);
        }
    }

    private static BufferedImage resultImage(RenderedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        return new BufferedImage(width, height, TYPE_INT_ARGB);
    }
}
