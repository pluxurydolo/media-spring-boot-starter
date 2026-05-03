package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import java.awt.image.RenderedImage;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;

public class ImageAudioMergerFrameRecorderConfigurer {
    public void configure(FFmpegFrameRecorder recorder, FFmpegFrameGrabber audioGrabber, RenderedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        recorder.setImageWidth(imageWidth);
        recorder.setImageHeight(imageHeight);

        int sampleRate = audioGrabber.getSampleRate();
        int audioChannels = audioGrabber.getAudioChannels();

        recorder.setVideoCodec(AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(30.0);
        recorder.setVideoBitrate(2_000_000);

        recorder.setAudioCodec(AV_CODEC_ID_AAC);
        recorder.setSampleRate(sampleRate);
        recorder.setAudioChannels(audioChannels);
        recorder.setAudioBitrate(128_000);
    }
}
