package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.ScaledWatermark;
import org.bytedeco.javacv.FFmpegFrameGrabber;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.math.BigDecimal;

import static java.awt.Image.SCALE_SMOOTH;
import static java.math.RoundingMode.HALF_UP;

public class WatermarkScaler {
    public ScaledWatermark scale(FFmpegFrameGrabber grabber, BufferedImage watermark) {
        int imageWidth = grabber.getImageWidth();
        return scale(imageWidth, watermark);
    }

    public ScaledWatermark scale(RenderedImage image, BufferedImage watermark) {
        int imageWidth = image.getWidth();
        return scale(imageWidth, watermark);
    }

    private static ScaledWatermark scale(int imageWidth, BufferedImage watermark) {
        int scaledWidth = scaledWidth(imageWidth);
        int scaledHeight = scaledHeight(watermark, scaledWidth);
        Image scaledImage = watermark.getScaledInstance(scaledWidth, scaledHeight, SCALE_SMOOTH);
        return new ScaledWatermark(scaledWidth, scaledHeight, scaledImage);
    }

    private static int scaledWidth(int imageWidth) {
        double scale = 0.1;

        return BigDecimal.valueOf(imageWidth * scale)
            .setScale(0, HALF_UP)
            .intValue();
    }

    private static int scaledHeight(RenderedImage watermark, int scaledWidth) {
        int watermarkWidth = watermark.getWidth();
        int watermarkHeight = watermark.getHeight();
        return watermarkHeight * scaledWidth / watermarkWidth;
    }
}
