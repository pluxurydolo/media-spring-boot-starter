package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.ScaledWatermark;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.RenderedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WatermarkImageAdderTests {
    private static final WatermarkImageAdder ADDER = new WatermarkImageAdder();

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> ADDER.add(renderedImage(), scaledWatermark(), graphics2D()));
    }

    private static RenderedImage renderedImage() {
        RenderedImage mock = mock(RenderedImage.class);

        when(mock.getWidth())
            .thenReturn(1);
        when(mock.getHeight())
            .thenReturn(1);

        return mock;
    }

    private static ScaledWatermark scaledWatermark() {
        return new ScaledWatermark(1, 1, image());
    }

    private static Graphics2D graphics2D() {
        Graphics2D mock = mock(Graphics2D.class);

        doNothing()
            .when(mock).setComposite(any());
        when(mock.drawImage(any(Image.class), anyInt(), anyInt(), isNull()))
            .thenReturn(true);

        return mock;
    }

    private static Image image() {
        return mock(Image.class);
    }
}
