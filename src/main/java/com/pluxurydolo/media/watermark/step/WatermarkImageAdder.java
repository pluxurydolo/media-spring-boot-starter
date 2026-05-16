package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.Container;
import com.pluxurydolo.media.dto.Element;
import com.pluxurydolo.media.dto.ScaledWatermark;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.RenderedImage;

import static com.pluxurydolo.media.dto.Position.BOTTOM_RIGHT;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;

public class WatermarkImageAdder {
    public void add(RenderedImage image, ScaledWatermark watermark, Graphics2D graphics2D) {
        float opacity = 0.8F;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        int watermarkWidth = watermark.width();
        int watermarkHeight = watermark.height();
        Image watermarkImage = watermark.image();

        Container container = new Container(imageWidth, imageHeight);
        Element element = new Element(watermarkWidth, watermarkHeight);
        int padding = 20;

        Point positionPoint = BOTTOM_RIGHT.getPoint(container, element, padding);

        graphics2D.setComposite(getInstance(SRC_OVER, opacity));
        graphics2D.drawImage(watermarkImage, positionPoint.x, positionPoint.y, null);
    }
}
