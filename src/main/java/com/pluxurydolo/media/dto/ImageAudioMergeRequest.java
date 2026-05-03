package com.pluxurydolo.media.dto;

import java.io.InputStream;

public record ImageAudioMergeRequest(
    String videoName,
    InputStream imageStream,
    InputStream audioStream
) {
}
