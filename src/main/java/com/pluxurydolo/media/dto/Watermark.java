package com.pluxurydolo.media.dto;

public record Watermark(
    byte[] image,
    String text,
    Position position
) {
}
