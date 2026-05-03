package com.pluxurydolo.media.hls;

import com.pluxurydolo.media.configurer.HLSFrameRecorderConfigurer;
import com.pluxurydolo.media.dto.HLSRequest;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.createTempFile;
import static java.io.File.separator;
import static java.lang.String.format;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.notExists;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class HLSConverter {
    private final HLSFrameRecorderConfigurer configurer;

    public HLSConverter(HLSFrameRecorderConfigurer configurer) {
        this.configurer = configurer;
    }

    public String convert(HLSRequest request) throws IOException {
        InputStream media = request.media();
        String videoName = request.videoName();
        double hlsPeriod = request.hlsPeriod();

        String hlsDirectory = format("%s%s%s", "hls", separator, videoName);
        Path hlsPath = Paths.get(hlsDirectory);

        if (notExists(hlsPath)) {
            createDirectories(hlsPath);
        }

        File tempFile = createTempFile("hls_input_" + videoName, ".tmp");
        Path tempFilePath = tempFile.toPath();

        copy(media, tempFilePath, REPLACE_EXISTING);

        String hlsPlaylistPath = format("%s%s%s", hlsDirectory, separator, "playlist.m3u8");

        try (
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(tempFile);
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(hlsPlaylistPath, 0, 0)
        ) {
            grabber.start();

            configurer.configure(recorder, grabber, hlsDirectory, hlsPeriod);

            recorder.start();

            for (Frame frame = grabber.grab(); frame != null; frame = grabber.grab()) {
                recorder.record(frame);
            }

            return hlsPlaylistPath;
        } finally {
            deleteIfExists(tempFilePath);
        }
    }
}
