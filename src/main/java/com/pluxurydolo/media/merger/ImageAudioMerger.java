package com.pluxurydolo.media.merger;

import com.pluxurydolo.media.configurer.ImageAudioMergerFrameRecorderConfigurer;
import com.pluxurydolo.media.dto.request.ImageAudioMergeRequest;
import com.pluxurydolo.media.exception.CreateFrameException;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class ImageAudioMerger {
    private final ImageAudioMergerFrameRecorderConfigurer configurer;

    public ImageAudioMerger(ImageAudioMergerFrameRecorderConfigurer configurer) {
        this.configurer = configurer;
    }

    public byte[] merge(ImageAudioMergeRequest request) throws IOException {
        byte[] imageBytes = request.image();
        byte[] audioBytes = request.audio();

        Path tempInputImage = Files.createTempFile("image-", ".jpg");
        Path tempInputAudio = Files.createTempFile("audio-", ".mp3");
        Path tempOutputVideo = Files.createTempFile("output-", ".mp4");

        Files.write(tempInputImage, imageBytes);
        Files.write(tempInputAudio, audioBytes);

        File tempInputImageFile = tempInputImage.toFile();
        File tempInputAudioFile = tempInputAudio.toFile();
        File tempOutputVideoFile = tempOutputVideo.toFile();

        try (
            FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(tempInputAudioFile);
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(tempOutputVideoFile, 0, 0);
            Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            BufferedImage image = ImageIO.read(tempInputImageFile);

            audioGrabber.start();

            configurer.configure(recorder, audioGrabber, image);

            recorder.start();

            createFrames(audioGrabber, converter, recorder, image);

            for (Frame audioFrame = audioGrabber.grab(); audioFrame != null; audioFrame = audioGrabber.grab()) {
                recorder.record(audioFrame);
            }

            return Files.readAllBytes(tempOutputVideo);
        } finally {
            Files.deleteIfExists(tempInputImage);
            Files.deleteIfExists(tempInputAudio);
            Files.deleteIfExists(tempOutputVideo);
        }
    }

    private static void createFrames(
        FFmpegFrameGrabber audioGrabber,
        Java2DFrameConverter converter,
        FFmpegFrameRecorder recorder,
        BufferedImage image
    ) {
        int framesPerSecond = 30;
        Frame imageFrame = converter.convert(image);

        double durationSeconds = audioGrabber.getLengthInTime() / 1_000_000.0;
        int totalFrames = (int) (durationSeconds * framesPerSecond);

        IntStream.range(0, totalFrames)
            .forEach(_ -> createFrame(recorder, imageFrame));
    }

    private static void createFrame(FFmpegFrameRecorder recorder, Frame imageFrame) {
        try {
            recorder.record(imageFrame);
        } catch (FFmpegFrameRecorder.Exception exception) {
            throw new CreateFrameException(exception);
        }
    }
}
