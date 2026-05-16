package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.request.ImageAudioMergeRequest;
import com.pluxurydolo.media.exception.ImageAudioMergeException;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class ImageAudioMergeClientTests {

    @Mock
    private ImageAudioMerger imageAudioMerger;

    @InjectMocks
    private ImageAudioMergeClient imageAudioMergeClient;

    @Test
    void testMergeImageAudio() throws IOException {
        byte[] bytes = {};
        when(imageAudioMerger.merge(any()))
            .thenReturn(bytes);

        Mono<byte[]> result = imageAudioMergeClient.mergeImageAudio(imageAudioMergeRequest());

        create(result)
            .expectNext(bytes)
            .verifyComplete();
    }

    @Test
    void testMergeImageAudioWhenExceptionOccurred() throws IOException {
        doThrow(RuntimeException.class)
            .when(imageAudioMerger).merge(any());

        Mono<byte[]> result = imageAudioMergeClient.mergeImageAudio(imageAudioMergeRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ImageAudioMergeException.class));
    }

    private static ImageAudioMergeRequest imageAudioMergeRequest() {
        byte[] bytes = {};
        return new ImageAudioMergeRequest(bytes, bytes);
    }
}
