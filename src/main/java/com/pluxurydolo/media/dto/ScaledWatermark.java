package com.pluxurydolo.media.dto;

import java.awt.Image;

public record ScaledWatermark(
    int width,
    int height,
    Image image
) {
}
