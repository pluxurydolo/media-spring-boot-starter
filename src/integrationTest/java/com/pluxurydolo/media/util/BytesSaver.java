package com.pluxurydolo.media.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class BytesSaver {
    private static final Path IMAGE_DIRECTORY = Paths.get("result/image");
    private static final Path VIDEO_DIRECTORY = Paths.get("result/video");

    static {
        try {
            Files.createDirectories(IMAGE_DIRECTORY);
            Files.createDirectories(VIDEO_DIRECTORY);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public static Path saveImage(byte[] imageBytes) {
        String filename = "image.jpg";
        Path filePath = IMAGE_DIRECTORY.resolve(filename);
        write(filePath, imageBytes);
        return filePath;
    }

    public static Path saveVideo(byte[] videoBytes) {
        String filename = "video.mp4";
        Path filePath = VIDEO_DIRECTORY.resolve(filename);
        write(filePath, videoBytes);
        return filePath;
    }

    private static void write(Path path, byte[] bytes) {
        try {
            Files.write(path, bytes, CREATE, TRUNCATE_EXISTING);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
