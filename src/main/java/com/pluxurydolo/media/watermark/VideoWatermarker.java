package com.pluxurydolo.media.watermark;

import com.pluxurydolo.media.configurer.VideoWatermarkFrameRecorderConfigurer;
import com.pluxurydolo.media.dto.ScaledWatermark;
import com.pluxurydolo.media.dto.request.VideoWatermarkRequest;
import com.pluxurydolo.media.dto.Watermark;
import com.pluxurydolo.media.exception.AddAudioException;
import com.pluxurydolo.media.exception.AddWatermarkException;
import com.pluxurydolo.media.watermark.step.WatermarkImageAdder;
import com.pluxurydolo.media.watermark.step.WatermarkScaler;
import com.pluxurydolo.media.watermark.step.WatermarkTextAdder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class VideoWatermarker {
    private final VideoWatermarkFrameRecorderConfigurer configurer;
    private final WatermarkScaler watermarkScaler;
    private final WatermarkImageAdder watermarkImageAdder;
    private final WatermarkTextAdder watermarkTextAdder;

    public VideoWatermarker(
        VideoWatermarkFrameRecorderConfigurer configurer,
        WatermarkScaler watermarkScaler,
        WatermarkImageAdder watermarkImageAdder,
        WatermarkTextAdder watermarkTextAdder
    ) {
        this.configurer = configurer;
        this.watermarkScaler = watermarkScaler;
        this.watermarkImageAdder = watermarkImageAdder;
        this.watermarkTextAdder = watermarkTextAdder;
    }

    public byte[] watermark(VideoWatermarkRequest request) throws IOException {
        byte[] videoBytes = request.video();

        Watermark watermark = request.watermark();
        byte[] watermarkImageBytes = watermark.image();

        Path inputVideoPath = Files.createTempFile("input_", ".mp4");
        Path outputVideoPath = Files.createTempFile("output_", ".mp4");

        Files.write(inputVideoPath, videoBytes);

        File inputVideoFile = inputVideoPath.toFile();
        File outputVideoFile = outputVideoPath.toFile();

        try (
            InputStream watermarkInputStream = new ByteArrayInputStream(watermarkImageBytes);
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputVideoFile);
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputVideoFile, 0, 0);
            Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            grabber.start();

            configurer.configure(recorder, grabber);
            recorder.start();

            BufferedImage watermarkImage = ImageIO.read(watermarkInputStream);
            ScaledWatermark scaledWatermark = watermarkScaler.scale(grabber, watermarkImage);
            addWatermark(grabber, converter, recorder, watermark, scaledWatermark);

            grabber.restart();

            addAudio(grabber, recorder);

            recorder.stop();
            grabber.stop();

            return Files.readAllBytes(outputVideoPath);
        } finally {
            Files.deleteIfExists(inputVideoPath);
            Files.deleteIfExists(outputVideoPath);
        }
    }

    private void addWatermark(
        FFmpegFrameGrabber grabber,
        Java2DFrameConverter converter,
        FFmpegFrameRecorder recorder,
        Watermark watermark,
        ScaledWatermark scaledWatermark
    ) {
        try {
            for (Frame frame = grabber.grabImage(); frame != null; frame = grabber.grabImage()) {
                BufferedImage image = converter.convert(frame);

                Graphics2D graphics2D = image.createGraphics();

                watermarkImageAdder.add(image, scaledWatermark, graphics2D);
                watermarkTextAdder.add(image, graphics2D, watermark);

                graphics2D.dispose();

                Frame outputFrame = converter.convert(image);
                outputFrame.timestamp = frame.timestamp;

                recorder.record(outputFrame);
            }
        } catch (FFmpegFrameGrabber.Exception | FFmpegFrameRecorder.Exception exception) {
            throw new AddWatermarkException(exception);
        }
    }

    private static void addAudio(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder) {
        try {
            for (Frame audioFrame = grabber.grabSamples(); audioFrame != null; audioFrame = grabber.grabSamples()) {
                recorder.record(audioFrame);
            }
        } catch (FFmpegFrameGrabber.Exception | FFmpegFrameRecorder.Exception exception) {
            throw new AddAudioException(exception);
        }
    }
}
