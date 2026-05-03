package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.RenderedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageAudioMergerFrameRecorderConfigurerTests {
    private static final ImageAudioMergerFrameRecorderConfigurer CONFIGURER =
        new ImageAudioMergerFrameRecorderConfigurer();

    @Test
    void testConfigure() {
        assertDoesNotThrow(() -> CONFIGURER.configure(ffmpegFrameRecorder(), ffmpegFrameGrabber(), renderedImage()));
    }

    private static FFmpegFrameRecorder ffmpegFrameRecorder() {
        FFmpegFrameRecorder mock = mock(FFmpegFrameRecorder.class);

        doNothing()
            .when(mock).setImageHeight(anyInt());
        doNothing()
            .when(mock).setImageWidth(anyInt());
        doNothing()
            .when(mock).setVideoCodec(anyInt());
        doNothing()
            .when(mock).setFormat(anyString());
        doNothing()
            .when(mock).setFrameRate(anyDouble());
        doNothing()
            .when(mock).setVideoBitrate(anyInt());
        doNothing()
            .when(mock).setAudioCodec(anyInt());
        doNothing()
            .when(mock).setSampleRate(anyInt());
        doNothing()
            .when(mock).setAudioChannels(anyInt());
        doNothing()
            .when(mock).setAudioBitrate(anyInt());

        return mock;
    }

    private static FFmpegFrameGrabber ffmpegFrameGrabber() {
        FFmpegFrameGrabber mock = mock(FFmpegFrameGrabber.class);

        when(mock.getSampleRate())
            .thenReturn(1);
        when(mock.getAudioChannels())
            .thenReturn(1);

        return mock;
    }

    private static RenderedImage renderedImage() {
        RenderedImage mock = mock(RenderedImage.class);

        when(mock.getWidth())
            .thenReturn(1);
        when(mock.getHeight())
            .thenReturn(1);

        return mock;
    }
}
