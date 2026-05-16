package com.pluxurydolo.media.watermark.step;

import com.pluxurydolo.media.dto.Container;
import com.pluxurydolo.media.dto.Element;
import com.pluxurydolo.media.dto.Watermark;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.RenderedImage;

import static com.pluxurydolo.media.dto.Position.BOTTOM;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

public class WatermarkTextAdder {
    public void add(RenderedImage image, Graphics2D graphics, Watermark watermark) {
        float opacity = 0.4F;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        String watermarkText = watermark.text();

        int fontSize = 24;
        Font font = new Font("Arial", BOLD, fontSize);
        graphics.setFont(font);
        graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics metrics = graphics.getFontMetrics(font);
        int textWidth = metrics.stringWidth(watermarkText);
        int textHeight = metrics.getHeight();

        Container container = new Container(imageWidth, imageHeight);
        Element element = new Element(textWidth, textHeight);

        int padding = 0;
        Point positionPoint = BOTTOM.getPoint(container, element, padding);

        drawShadow(graphics, opacity, watermarkText, positionPoint);
        drawText(graphics, watermarkText, positionPoint);
    }

    private static void drawShadow(Graphics2D graphics, float opacity, String watermarkText, Point positionPoint) {
        graphics.setComposite(getInstance(SRC_OVER, opacity));
        graphics.setColor(BLACK);
        graphics.drawString(watermarkText, positionPoint.x + 2, positionPoint.y + 2);
    }

    private static void drawText(Graphics2D graphics, String watermarkText, Point positionPoint) {
        graphics.setColor(WHITE);
        graphics.drawString(watermarkText, positionPoint.x, positionPoint.y);
    }
}
