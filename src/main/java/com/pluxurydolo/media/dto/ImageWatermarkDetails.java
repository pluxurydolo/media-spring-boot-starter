package com.pluxurydolo.media.dto;

import java.awt.image.BufferedImage;

public record ImageWatermarkDetails(
    BufferedImage originalImage,
    BufferedImage watermarkImage,
    BufferedImage resultImage
) {
}
