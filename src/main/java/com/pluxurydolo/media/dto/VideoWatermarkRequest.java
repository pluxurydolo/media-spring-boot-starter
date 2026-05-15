package com.pluxurydolo.media.dto;

public record VideoWatermarkRequest(
    String videoName,
    byte[] video,
    Watermark watermark
) {
}
