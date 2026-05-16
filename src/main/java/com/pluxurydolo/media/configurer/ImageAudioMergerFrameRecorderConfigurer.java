package com.pluxurydolo.media.configurer;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import java.awt.image.RenderedImage;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;

public class ImageAudioMergerFrameRecorderConfigurer {
    public void configure(FFmpegFrameRecorder recorder, FFmpegFrameGrabber audioGrabber, RenderedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        int sampleRate = audioGrabber.getSampleRate();
        int audioChannels = audioGrabber.getAudioChannels();

        recorder.setImageWidth(imageWidth);
        recorder.setImageHeight(imageHeight);

        recorder.setVideoCodec(AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(30);
        recorder.setVideoBitrate(2_000_000);
        recorder.setPixelFormat(AV_PIX_FMT_YUV420P);

        recorder.setAudioChannels(audioChannels);
        recorder.setSampleRate(sampleRate);
        recorder.setAudioCodec(AV_CODEC_ID_AAC);
        recorder.setAudioBitrate(128_000);

        recorder.setOption("crf", "18");
        recorder.setOption("preset", "medium");
    }
}
