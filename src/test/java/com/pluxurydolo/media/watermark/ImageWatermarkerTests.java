package com.pluxurydolo.media.watermark;

import com.pluxurydolo.media.dto.ImageWatermarkDetails;
import com.pluxurydolo.media.dto.ScaledWatermark;
import com.pluxurydolo.media.dto.Watermark;
import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.watermark.step.WatermarkDetailsRetriever;
import com.pluxurydolo.media.watermark.step.WatermarkImageAdder;
import com.pluxurydolo.media.watermark.step.WatermarkScaler;
import com.pluxurydolo.media.watermark.step.WatermarkTextAdder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Image;
import java.awt.image.BufferedImage;

import static com.pluxurydolo.media.dto.Position.CENTER;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageWatermarkerTests {

    @Mock
    private WatermarkDetailsRetriever watermarkDetailsRetriever;

    @Mock
    private WatermarkScaler watermarkScaler;

    @Mock
    private WatermarkImageAdder watermarkImageAdder;

    @Mock
    private WatermarkTextAdder watermarkTextAdder;

    @InjectMocks
    private ImageWatermarker imageWatermarker;

    @Test
    void testWatermark() {
        doNothing()
            .when(watermarkImageAdder).add(any(), any(), any());
        doNothing()
            .when(watermarkTextAdder).add(any(), any(), any());
        when(watermarkDetailsRetriever.retrieve(any()))
            .thenReturn(imageWatermarkDetails());
        when(watermarkScaler.scale(any(BufferedImage.class), any(BufferedImage.class)))
            .thenReturn(scaledWatermark());

        byte[] result = imageWatermarker.watermark(imageWatermarkRequest());

        assertThat(result)
            .hasSize(69);
    }

    private static ImageWatermarkRequest imageWatermarkRequest() {
        byte[] bytes = {};
        return new ImageWatermarkRequest(bytes, watermark());
    }

    private static Watermark watermark() {
        byte[] bytes = {};
        return new Watermark(bytes, "text", CENTER);
    }

    private static ImageWatermarkDetails imageWatermarkDetails() {
        BufferedImage bufferedImage = new BufferedImage(1, 1, TYPE_INT_RGB);
        return new ImageWatermarkDetails(bufferedImage, bufferedImage, bufferedImage);
    }

    private static ScaledWatermark scaledWatermark() {
        Image mock = mock(Image.class);
        return new ScaledWatermark(1, 1, mock);
    }
}
