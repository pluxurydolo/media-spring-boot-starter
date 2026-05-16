package com.pluxurydolo.media.dto;

import org.junit.jupiter.api.Test;

import java.awt.Point;

import static com.pluxurydolo.media.dto.Position.BOTTOM;
import static com.pluxurydolo.media.dto.Position.BOTTOM_LEFT;
import static com.pluxurydolo.media.dto.Position.BOTTOM_RIGHT;
import static com.pluxurydolo.media.dto.Position.CENTER;
import static com.pluxurydolo.media.dto.Position.LEFT;
import static com.pluxurydolo.media.dto.Position.RIGHT;
import static com.pluxurydolo.media.dto.Position.TOP;
import static com.pluxurydolo.media.dto.Position.TOP_LEFT;
import static com.pluxurydolo.media.dto.Position.TOP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

class PositionTests {

    @Test
    void testGetPoint() {
        Point topLeft = TOP_LEFT.getPoint(container(), element(), 1);
        Point top = TOP.getPoint(container(), element(), 1);
        Point topRight = TOP_RIGHT.getPoint(container(), element(), 1);
        Point left = LEFT.getPoint(container(), element(), 1);
        Point center = CENTER.getPoint(container(), element(), 1);
        Point right = RIGHT.getPoint(container(), element(), 1);
        Point bottomLeft = BOTTOM_LEFT.getPoint(container(), element(), 1);
        Point bottom = BOTTOM.getPoint(container(), element(), 1);
        Point bottomRight = BOTTOM_RIGHT.getPoint(container(), element(), 1);

        assertThat(topLeft)
            .isEqualTo(point(1, 1));
        assertThat(top)
            .isEqualTo(point(2, 1));
        assertThat(topRight)
            .isEqualTo(point(4, 1));
        assertThat(left)
            .isEqualTo(point(1, 2));
        assertThat(center)
            .isEqualTo(point(2, 2));
        assertThat(right)
            .isEqualTo(point(4, 2));
        assertThat(bottomLeft)
            .isEqualTo(point(1, 4));
        assertThat(bottom)
            .isEqualTo(point(2, 4));
        assertThat(bottomRight)
            .isEqualTo(point(4, 4));
    }

    private static Container container() {
        return new Container(10, 10);
    }

    private static Element element() {
        return new Element(5, 5);
    }

    private static Point point(int xCoord, int yCoord) {
        return new Point(xCoord, yCoord);
    }
}
