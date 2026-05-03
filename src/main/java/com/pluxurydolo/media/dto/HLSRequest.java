package com.pluxurydolo.media.dto;

import java.io.InputStream;

public record HLSRequest(
    String videoName,
    InputStream media,
    double hlsPeriod
) {
}
