package com.pluxurydolo.media.dto;

public record ImageWatermarkRequest(
    String imageName,
    byte[] image,
    Watermark watermark
) {
}
