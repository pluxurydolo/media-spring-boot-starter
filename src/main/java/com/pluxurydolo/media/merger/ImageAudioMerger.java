package com.pluxurydolo.media.merger;

import com.pluxurydolo.media.configurer.ImageAudioMergerFrameRecorderConfigurer;
import com.pluxurydolo.media.dto.ImageAudioMergeRequest;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ImageAudioMerger {
    private final ImageAudioMergerFrameRecorderConfigurer configurer;

    public ImageAudioMerger(ImageAudioMergerFrameRecorderConfigurer configurer) {
        this.configurer = configurer;
    }

    public String merge(ImageAudioMergeRequest request) throws IOException {
        String videoName = request.videoName();
        InputStream imageStream = request.imageStream();
        InputStream audioStream = request.audioStream();

        Path videosPath = Paths.get("videos/");

        if (Files.notExists(videosPath)) {
            Files.createDirectories(videosPath);
        }

        File tempImage = File.createTempFile("image-", ".jpg");
        Path tempImagePath = tempImage.toPath();
        Files.copy(imageStream, tempImagePath, REPLACE_EXISTING);

        File tempAudio = File.createTempFile("audio-", ".mp3");
        Path tempAudioPath = tempAudio.toPath();
        Files.copy(audioStream, tempAudioPath, REPLACE_EXISTING);

        File outputVideo = new File(videosPath.toFile(), videoName + ".mp4");

        try (
            FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(tempAudio);
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputVideo, 0, 0);
            Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            BufferedImage image = ImageIO.read(tempImage);

            audioGrabber.start();

            configurer.configure(recorder, audioGrabber, image);

            recorder.start();

            createFrames(audioGrabber, converter, recorder, image);

            for (Frame audioFrame = audioGrabber.grab(); audioFrame != null; audioFrame = audioGrabber.grab()) {
                recorder.record(audioFrame);
            }

            return outputVideo.getAbsolutePath();
        } finally {
            Files.deleteIfExists(tempImagePath);
            Files.deleteIfExists(tempAudioPath);
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
            throw new IllegalStateException(exception);
        }
    }
}
