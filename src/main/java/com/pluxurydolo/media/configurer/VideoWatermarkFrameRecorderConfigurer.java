package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;

public class VideoWatermarkFrameRecorderConfigurer {
    public void configure(FFmpegFrameRecorder recorder, FFmpegFrameGrabber grabber) {
        int width = grabber.getImageWidth();
        int height = grabber.getImageHeight();
        double frameRate = grabber.getFrameRate();
        int audioChannels = grabber.getAudioChannels();
        int sampleRate = grabber.getSampleRate();

        recorder.setImageWidth(width);
        recorder.setImageHeight(height);
        recorder.setFormat("mp4");
        recorder.setVideoCodec(AV_CODEC_ID_H264);
        recorder.setVideoBitrate(2_000_000);
        recorder.setFrameRate(frameRate);
        recorder.setPixelFormat(AV_PIX_FMT_YUV420P);
        recorder.setOption("crf", "18");
        recorder.setOption("preset", "medium");

        recorder.setAudioChannels(audioChannels);
        recorder.setSampleRate(sampleRate);
        recorder.setAudioCodec(AV_CODEC_ID_AAC);
        recorder.setAudioBitrate(128_000);
    }
}
