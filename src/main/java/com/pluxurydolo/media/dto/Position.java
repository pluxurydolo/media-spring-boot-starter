package com.pluxurydolo.media.dto;

import java.awt.Point;

public enum Position {
    TOP_LEFT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            return new Point(padding, padding);
        }
    },
    TOP {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();

            int xCoord = (containerWidth - elementWidth) / 2;

            return new Point(xCoord, padding);
        }
    },
    TOP_RIGHT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();

            int xCoord = containerWidth - elementWidth - padding;

            return new Point(xCoord, padding);
        }
    },
    LEFT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerHeight = container.height();
            int elementHeight = element.height();

            int yCoord = (containerHeight - elementHeight) / 2;

            return new Point(padding, yCoord);
        }
    },
    CENTER {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();
            int containerHeight = container.height();
            int elementHeight = element.height();

            int xCoord = (containerWidth - elementWidth) / 2;
            int yCoord = (containerHeight - elementHeight) / 2;

            return new Point(xCoord, yCoord);
        }
    },
    RIGHT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();
            int containerHeight = container.height();
            int elementHeight = element.height();

            int xCoord = containerWidth - elementWidth - padding;
            int yCoord = (containerHeight - elementHeight) / 2;

            return new Point(xCoord, yCoord);
        }
    },
    BOTTOM_LEFT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerHeight = container.height();
            int elementHeight = element.height();

            int yCoord = containerHeight - elementHeight - padding;

            return new Point(padding, yCoord);
        }
    },
    BOTTOM {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();
            int containerHeight = container.height();
            int elementHeight = element.height();

            int xCoord = (containerWidth - elementWidth) / 2;
            int yCoord = containerHeight - elementHeight - padding;

            return new Point(xCoord, yCoord);
        }
    },
    BOTTOM_RIGHT {
        @Override
        public Point getPoint(Container container, Element element, int padding) {
            int containerWidth = container.width();
            int elementWidth = element.width();
            int containerHeight = container.height();
            int elementHeight = element.height();

            int xCoord = containerWidth - elementWidth - padding;
            int yCoord = containerHeight - elementHeight - padding;

            return new Point(xCoord, yCoord);
        }
    };

    public abstract Point getPoint(Container container, Element element, int padding);
}
