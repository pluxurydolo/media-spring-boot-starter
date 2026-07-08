package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.request.HTMLScreenshotRequest;
import com.pluxurydolo.media.exception.HTMLScreenshotException;
import com.pluxurydolo.media.screenshot.HTMLScreenshotter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class HTMLScreenshotClientTests {

    @Mock
    private HTMLScreenshotter htmlScreenshotter;

    @InjectMocks
    private HTMLScreenshotClient htmlScreenshotClient;

    @Test
    void testCreateScreenshot() {
        byte[] bytes = {};
        when(htmlScreenshotter.createScreenshot(any()))
            .thenReturn(bytes);

        Mono<byte[]> result = htmlScreenshotClient.createScreenshot(htmlScreenshotRequest());

        create(result)
            .expectNext(bytes)
            .verifyComplete();
    }

    @Test
    void testCreateScreenshotWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(htmlScreenshotter).createScreenshot(any());

        Mono<byte[]> result = htmlScreenshotClient.createScreenshot(htmlScreenshotRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(HTMLScreenshotException.class));
    }

    private static HTMLScreenshotRequest htmlScreenshotRequest() {
        return new HTMLScreenshotRequest("htmlContent");
    }
}
