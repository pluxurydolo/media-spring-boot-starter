package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HLSFrameRecorderConfigurerTests {
    private static final HLSFrameRecorderConfigurer CONFIGURER = new HLSFrameRecorderConfigurer();

    @Test
    void testConfigureVideoWithAudio() {
        assertDoesNotThrow(
            () -> CONFIGURER.configure(
                videoWithAudioFrameRecorder(),
                videoWithAudioFrameGrabber(),
                "hls/video",
                1.0
            )
        );
    }

    @Test
    void testConfigureVideoOnly() {
        assertDoesNotThrow(
            () -> CONFIGURER.configure(
                videoOnlyFrameRecorder(),
                videoOnlyFrameGrabber(),
                "hls/video",
                1.0
            )
        );
    }

    @Test
    void testConfigureAudioOnly() {
        assertDoesNotThrow(
            () -> CONFIGURER.configure(
                audioOnlyFrameRecorder(),
                audioOnlyFrameGrabber(),
                "hls/video",
                1.0
            )
        );
    }

    @Test
    void testConfigureNoVideoNoAudio() {
        assertDoesNotThrow(
            () -> CONFIGURER.configure(
                noVideoNoAudioFrameRecorder(),
                noVideoNoAudioFrameGrabber(),
                "hls/audio",
                1.0
            )
        );
    }

    private static FFmpegFrameRecorder videoWithAudioFrameRecorder() {
        FFmpegFrameRecorder mock = ffmpegFrameRecorder(10, 10);

        doNothing()
            .when(mock).setVideoCodec(27);
        doNothing()
            .when(mock).setFrameRate(30.0);
        doNothing()
            .when(mock).setVideoBitrate(2_000_000);
        doNothing()
            .when(mock).setAudioCodec(86018);
        doNothing()
            .when(mock).setSampleRate(1);
        doNothing()
            .when(mock).setAudioChannels(1);
        doNothing()
            .when(mock).setAudioBitrate(128_000);

        return mock;
    }

    private static FFmpegFrameRecorder videoOnlyFrameRecorder() {
        FFmpegFrameRecorder mock = ffmpegFrameRecorder(10, 10);

        doNothing()
            .when(mock).setVideoCodec(27);
        doNothing()
            .when(mock).setFrameRate(30.0);
        doNothing()
            .when(mock).setVideoBitrate(2_000_000);
        doNothing()
            .when(mock).setAudioCodec(0);

        return mock;
    }

    private static FFmpegFrameRecorder audioOnlyFrameRecorder() {
        FFmpegFrameRecorder mock = ffmpegFrameRecorder(0, 0);

        doNothing()
            .when(mock).setVideoCodec(0);
        doNothing()
            .when(mock).setAudioCodec(86018);
        doNothing()
            .when(mock).setSampleRate(1);
        doNothing()
            .when(mock).setAudioChannels(1);
        doNothing()
            .when(mock).setAudioBitrate(128_000);

        return mock;
    }

    private static FFmpegFrameRecorder noVideoNoAudioFrameRecorder() {
        FFmpegFrameRecorder mock = ffmpegFrameRecorder(0, 0);

        doNothing()
            .when(mock).setVideoCodec(0);
        doNothing()
            .when(mock).setAudioCodec(0);

        return mock;
    }


    private static FFmpegFrameRecorder ffmpegFrameRecorder(int width, int height) {
        FFmpegFrameRecorder mock = mock(FFmpegFrameRecorder.class);

        doNothing()
            .when(mock).setImageWidth(width);
        doNothing()
            .when(mock).setImageHeight(height);
        doNothing()
            .when(mock).setFormat("hls");
        doNothing()
            .when(mock).setOption("hls_time", "1.0");
        doNothing()
            .when(mock).setOption("hls_flags", "independent_segments+split_by_time");
        doNothing()
            .when(mock).setOption("hls_segment_size", "0");
        doNothing()
            .when(mock).setOption("hls_list_size", "0");
        doNothing()
            .when(mock).setOption("hls_segment_type", "mpegts");
        doNothing()
            .when(mock).setOption("start_number", "0");
        doNothing()
            .when(mock).setOption("hls_segment_filename", "hls/video/segment_%d.ts");

        return mock;
    }

    private static FFmpegFrameGrabber videoWithAudioFrameGrabber() {
        return ffmpegFrameGrabber(10, 10, 30.0, 1);
    }

    private static FFmpegFrameGrabber videoOnlyFrameGrabber() {
        return ffmpegFrameGrabber(10, 10, 30.0, 0);
    }

    private static FFmpegFrameGrabber audioOnlyFrameGrabber() {
        return ffmpegFrameGrabber(0, 0, 0.0, 1);
    }

    private static FFmpegFrameGrabber noVideoNoAudioFrameGrabber() {
        return ffmpegFrameGrabber(0, 0, 0.0, 0);
    }

    private static FFmpegFrameGrabber ffmpegFrameGrabber(int width, int height, double frameRate, int sampleRate) {
        FFmpegFrameGrabber mock = mock(FFmpegFrameGrabber.class);

        when(mock.getImageWidth())
            .thenReturn(width);
        when(mock.getImageHeight())
            .thenReturn(height);
        when(mock.getFrameRate())
            .thenReturn(frameRate);
        when(mock.getSampleRate())
            .thenReturn(sampleRate);
        when(mock.getAudioChannels())
            .thenReturn(1);

        return mock;
    }
}
