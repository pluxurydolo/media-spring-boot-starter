package com.pluxurydolo.media.watermark;

import com.pluxurydolo.media.dto.ImageWatermarkDetails;
import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.dto.ScaledWatermark;
import com.pluxurydolo.media.dto.Watermark;
import com.pluxurydolo.media.exception.ConvertImageException;
import com.pluxurydolo.media.watermark.step.WatermarkDetailsRetriever;
import com.pluxurydolo.media.watermark.step.WatermarkImageAdder;
import com.pluxurydolo.media.watermark.step.WatermarkScaler;
import com.pluxurydolo.media.watermark.step.WatermarkTextAdder;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageWatermarker {
    private final WatermarkDetailsRetriever watermarkDetailsRetriever;
    private final WatermarkScaler watermarkScaler;
    private final WatermarkImageAdder watermarkImageAdder;
    private final WatermarkTextAdder watermarkTextAdder;

    public ImageWatermarker(
        WatermarkDetailsRetriever watermarkDetailsRetriever,
        WatermarkScaler watermarkScaler,
        WatermarkImageAdder watermarkImageAdder,
        WatermarkTextAdder watermarkTextAdder
    ) {
        this.watermarkDetailsRetriever = watermarkDetailsRetriever;
        this.watermarkScaler = watermarkScaler;
        this.watermarkImageAdder = watermarkImageAdder;
        this.watermarkTextAdder = watermarkTextAdder;
    }

    public byte[] watermark(ImageWatermarkRequest request) {
        Watermark watermark = request.watermark();

        ImageWatermarkDetails details = watermarkDetailsRetriever.retrieve(request);

        BufferedImage originalImage = details.originalImage();
        BufferedImage watermarkImage = details.watermarkImage();
        BufferedImage resultImage = details.resultImage();

        ScaledWatermark scaledWatermark = watermarkScaler.scale(originalImage, watermarkImage);

        Graphics2D graphics2D = resultImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, null);

        watermarkImageAdder.add(originalImage, scaledWatermark, graphics2D);
        watermarkTextAdder.add(originalImage, graphics2D, watermark);

        graphics2D.dispose();

        return convertResult(resultImage);
    }

    private static byte[] convertResult(RenderedImage resultImage) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(resultImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            throw new ConvertImageException(exception);
        }
    }
}
