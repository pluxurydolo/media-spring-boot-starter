package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import static java.io.File.separator;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_NONE;

public class HLSFrameRecorderConfigurer {
    public void configure(FFmpegFrameRecorder recorder, FFmpegFrameGrabber grabber, String hlsDirectory, double hlsPeriod) {
        int videoWidth = grabber.getImageWidth();
        int videoHeight = grabber.getImageHeight();
        double frameRate = frameRate(grabber.getFrameRate());
        int sampleRate = grabber.getSampleRate();
        int audioChannels = grabber.getAudioChannels();
        boolean hasVideo = videoWidth > 0 && videoHeight > 0;
        boolean hasAudio = sampleRate > 0;

        if (hasVideo) {
            recorder.setImageWidth(videoWidth);
            recorder.setImageHeight(videoHeight);
        } else {
            recorder.setImageWidth(1280);
            recorder.setImageHeight(720);
        }

        if (hasVideo) {
            recorder.setVideoCodec(AV_CODEC_ID_H264);
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(2_000_000);
        } else {
            recorder.setVideoCodec(AV_CODEC_ID_NONE);
        }

        if (hasAudio) {
            recorder.setAudioCodec(AV_CODEC_ID_AAC);
            recorder.setSampleRate(sampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(128_000);
        } else {
            recorder.setAudioCodec(AV_CODEC_ID_NONE);
        }

        recorder.setFormat("hls");
        recorder.setOption("hls_time", String.valueOf(hlsPeriod));
        recorder.setOption("hls_flags", "independent_segments+split_by_time");
        recorder.setOption("hls_segment_size", "0");
        recorder.setOption("hls_list_size", "0");
        recorder.setOption("hls_segment_type", "mpegts");
        recorder.setOption("start_number", "0");
        recorder.setOption("hls_segment_filename", hlsDirectory + separator + "segment_%d.ts");
    }

    private static double frameRate(double frameRate) {
        if (frameRate > 0.0) {
            return frameRate;
        }

        return 30.0;
    }
}
