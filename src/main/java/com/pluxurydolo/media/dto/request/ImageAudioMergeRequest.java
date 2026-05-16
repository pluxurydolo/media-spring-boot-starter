package com.pluxurydolo.media.dto.request;

public record ImageAudioMergeRequest(
    byte[] image,
    byte[] audio
) {
}
