package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VideoWatermarkFrameRecorderConfigurerTests {
    private static final VideoWatermarkFrameRecorderConfigurer CONFIGURER = new VideoWatermarkFrameRecorderConfigurer();

    @Test
    void testConfigure() {
        assertDoesNotThrow(() -> CONFIGURER.configure(ffmpegFrameRecorder(), ffmpegFrameGrabber()));
    }

    private static FFmpegFrameRecorder ffmpegFrameRecorder() {
        FFmpegFrameRecorder mock = mock(FFmpegFrameRecorder.class);

        doNothing()
            .when(mock).setImageWidth(1);
        doNothing()
            .when(mock).setImageHeight(1);
        doNothing()
            .when(mock).setFormat("mp4");
        doNothing()
            .when(mock).setVideoCodec(27);
        doNothing()
            .when(mock).setVideoBitrate(2_000_000);
        doNothing()
            .when(mock).setFrameRate(1.0);
        doNothing()
            .when(mock).setPixelFormat(0);
        doNothing()
            .when(mock).setOption("crf", "18");
        doNothing()
            .when(mock).setOption("preset", "medium");
        doNothing()
            .when(mock).setAudioChannels(1);
        doNothing()
            .when(mock).setSampleRate(1);
        doNothing()
            .when(mock).setAudioCodec(86018);
        doNothing()
            .when(mock).setAudioBitrate(128_000);

        return mock;
    }

    private static FFmpegFrameGrabber ffmpegFrameGrabber() {
        FFmpegFrameGrabber mock = mock(FFmpegFrameGrabber.class);

        when(mock.getImageWidth())
            .thenReturn(1);
        when(mock.getImageHeight())
            .thenReturn(1);
        when(mock.getFrameRate())
            .thenReturn(1.0);
        when(mock.getAudioChannels())
            .thenReturn(1);
        when(mock.getSampleRate())
            .thenReturn(1);

        return mock;
    }
}
