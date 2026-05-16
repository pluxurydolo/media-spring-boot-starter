package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.Watermark;
import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.dto.request.VideoWatermarkRequest;
import com.pluxurydolo.media.exception.ImageWatermarkException;
import com.pluxurydolo.media.exception.VideoWatermarkException;
import com.pluxurydolo.media.watermark.ImageWatermarker;
import com.pluxurydolo.media.watermark.VideoWatermarker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.pluxurydolo.media.dto.Position.CENTER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class WatermarkClientTests {

    @Mock
    private ImageWatermarker imageWatermarker;

    @Mock
    private VideoWatermarker videoWatermarker;

    @InjectMocks
    private WatermarkClient watermarkClient;

    @Test
    void testWatermarkImage() {
        byte[] bytes = {};
        when(imageWatermarker.watermark(any()))
            .thenReturn(bytes);

        Mono<byte[]> result = watermarkClient.watermarkImage(imageWatermarkRequest());

        create(result)
            .expectNext(bytes)
            .verifyComplete();
    }

    @Test
    void testWatermarkImageWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(imageWatermarker).watermark(any());

        Mono<byte[]> result = watermarkClient.watermarkImage(imageWatermarkRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(ImageWatermarkException.class));
    }

    @Test
    void testWatermarkVideo() throws IOException {
        byte[] bytes = {};
        when(videoWatermarker.watermark(any()))
            .thenReturn(bytes);

        Mono<byte[]> result = watermarkClient.watermarkVideo(videoWatermarkRequest());

        create(result)
            .expectNext(bytes)
            .verifyComplete();
    }

    @Test
    void testWatermarkVideoWhenExceptionOccurred() throws IOException {
        doThrow(RuntimeException.class)
            .when(videoWatermarker).watermark(any());

        Mono<byte[]> result = watermarkClient.watermarkVideo(videoWatermarkRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(VideoWatermarkException.class));
    }

    private static ImageWatermarkRequest imageWatermarkRequest() {
        byte[] bytes = {};
        return new ImageWatermarkRequest(bytes, watermark());
    }

    private static VideoWatermarkRequest videoWatermarkRequest() {
        byte[] bytes = {};
        return new VideoWatermarkRequest(bytes, watermark());
    }

    private static Watermark watermark() {
        byte[] bytes = {};
        return new Watermark(bytes, "text", CENTER);
    }
}
