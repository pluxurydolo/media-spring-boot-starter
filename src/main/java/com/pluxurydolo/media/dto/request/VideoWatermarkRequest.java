package com.pluxurydolo.media.dto.request;

import com.pluxurydolo.media.dto.Watermark;

public record VideoWatermarkRequest(
    byte[] video,
    Watermark watermark
) {
}
