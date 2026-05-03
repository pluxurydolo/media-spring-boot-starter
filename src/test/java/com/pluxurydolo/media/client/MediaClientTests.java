package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.HLSRequest;
import com.pluxurydolo.media.dto.ImageAudioMergeRequest;
import com.pluxurydolo.media.exception.HLSConverterException;
import com.pluxurydolo.media.exception.ImageAudioMergeException;
import com.pluxurydolo.media.hls.HLSConverter;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static java.io.InputStream.nullInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class MediaClientTests {

    @Mock
    private ImageAudioMerger imageAudioMerger;

    @Mock
    private HLSConverter hlsConverter;

    @InjectMocks
    private MediaClient mediaClient;

    @Test
    void testMergeImageAudio() throws IOException {
        when(imageAudioMerger.merge(any()))
            .thenReturn("");

        Mono<String> result = mediaClient.mergeImageAudio(imageAudioMergeRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testMergeImageAudioWhenExceptionOccurred() throws IOException {
        doThrow(RuntimeException.class)
            .when(imageAudioMerger).merge(any());

        Mono<String> result = mediaClient.mergeImageAudio(imageAudioMergeRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ImageAudioMergeException.class));
    }

    @Test
    void testConvertToHLS() throws IOException {
        when(hlsConverter.convert(any()))
            .thenReturn("");

        Mono<String> result = mediaClient.convertToHLS(hlsRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testConvertToHLSWhenExceptionOccurred() throws IOException {
        doThrow(RuntimeException.class)
            .when(hlsConverter).convert(any());

        Mono<String> result = mediaClient.convertToHLS(hlsRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(HLSConverterException.class));
    }

    private static ImageAudioMergeRequest imageAudioMergeRequest() {
        return new ImageAudioMergeRequest("videoName", nullInputStream(), nullInputStream());
    }

    private static HLSRequest hlsRequest() {
        return new HLSRequest("videoName", nullInputStream(), 1.0);
    }
}
