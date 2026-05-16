package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.Watermark;
import org.junit.jupiter.api.Test;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.RenderedImage;

import static com.pluxurydolo.media.dto.Position.CENTER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WatermarkTextAdderTests {
    private static final WatermarkTextAdder ADDER = new WatermarkTextAdder();

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> ADDER.add(renderedImage(), graphics2D(), watermark()));
    }

    private static RenderedImage renderedImage() {
        RenderedImage mock = mock(RenderedImage.class);

        when(mock.getWidth())
            .thenReturn(1);
        when(mock.getHeight())
            .thenReturn(1);

        return mock;
    }

    private static Graphics2D graphics2D() {
        Graphics2D graphics2D = mock(Graphics2D.class);
        FontMetrics fontMetrics = mock(FontMetrics.class);

        doNothing()
            .when(graphics2D).setFont(any());
        doNothing()
            .when(graphics2D).setRenderingHint(any(), any());
        doNothing()
            .when(graphics2D).setComposite(any());
        doNothing()
            .when(graphics2D).setColor(any());
        doNothing()
            .when(graphics2D).drawString(anyString(), anyInt(), anyInt());
        when(graphics2D.getFontMetrics(any()))
            .thenReturn(fontMetrics);
        when(fontMetrics.stringWidth(anyString()))
            .thenReturn(1);
        when(fontMetrics.getHeight())
            .thenReturn(1);

        return graphics2D;
    }

    private static Watermark watermark() {
        byte[] bytes = {};
        return new Watermark(bytes, "text", CENTER);
    }
}
