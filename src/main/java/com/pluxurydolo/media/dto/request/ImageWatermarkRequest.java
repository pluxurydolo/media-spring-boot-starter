package com.pluxurydolo.media.dto.request;

import com.pluxurydolo.media.dto.Watermark;

public record ImageWatermarkRequest(
    byte[] image,
    Watermark watermark
) {
}
