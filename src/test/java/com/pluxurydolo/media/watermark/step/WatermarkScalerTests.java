package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.ScaledWatermark;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.jupiter.api.Test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WatermarkScalerTests {
    private static final Image IMAGE = mock(Image.class);
    private static final WatermarkScaler SCALER = new WatermarkScaler();

    @Test
    void testScaleWithGrabber() {
        ScaledWatermark result = SCALER.scale(ffmpegFrameGrabber(), bufferedImage());

        assertThat(result)
            .isEqualTo(scaledWatermark());
    }

    @Test
    void testScaleWithImage() {
        ScaledWatermark result = SCALER.scale(renderedImage(), bufferedImage());

        assertThat(result)
            .isEqualTo(scaledWatermark());
    }

    private static FFmpegFrameGrabber ffmpegFrameGrabber() {
        FFmpegFrameGrabber mock = mock(FFmpegFrameGrabber.class);

        when(mock.getImageWidth())
            .thenReturn(10);

        return mock;
    }

    private static RenderedImage renderedImage() {
        RenderedImage mock = mock(RenderedImage.class);

        when(mock.getWidth())
            .thenReturn(10);

        return mock;
    }

    private static BufferedImage bufferedImage() {
        BufferedImage mock = mock(BufferedImage.class);

        when(mock.getWidth())
            .thenReturn(1);
        when(mock.getHeight())
            .thenReturn(1);
        when(mock.getScaledInstance(anyInt(), anyInt(), anyInt()))
            .thenReturn(IMAGE);

        return mock;
    }

    private static ScaledWatermark scaledWatermark() {
        return new ScaledWatermark(1, 1, IMAGE);
    }
}
