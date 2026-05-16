package com.pluxurydolo.media.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.media.base.AbstractIntegrationTests;
import com.pluxurydolo.media.dto.Watermark;
import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.dto.request.VideoWatermarkRequest;
import com.pluxurydolo.media.util.BytesSaver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.pluxurydolo.media.dto.Position.CENTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class WatermarkClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER = (Logger) getLogger(WatermarkClient.class);

    @Autowired
    private WatermarkClient watermarkClient;

    @Test
    void testWatermarkImage() throws IOException {
        List<ILoggingEvent> logs = listAppender().list;

        byte[] result = watermarkClient.watermarkImage(imageWatermarkRequest())
            .block();
        Path path = BytesSaver.saveImage(result);
        File file = path.toFile();

        assertThat(file)
            .exists()
            .hasSize(2351431L);

        assertThat(logs)
            .hasSize(1);

        assertThat(logs.getFirst().getFormattedMessage())
            .isEqualTo("sjzi [media-starter] Вотермарка успешно нанесена на картинку");
    }

    @Test
    void testWatermarkVideo() throws IOException {
        List<ILoggingEvent> logs = listAppender().list;

        byte[] result = watermarkClient.watermarkVideo(videoWatermarkRequest())
            .block();
        Path path = BytesSaver.saveVideo(result);
        File file = path.toFile();

        assertThat(file)
            .exists()
            .hasSize(281470L);

        assertThat(logs)
            .hasSize(1);

        assertThat(logs.getFirst().getFormattedMessage())
            .isEqualTo("crcp [media-starter] Вотермарка успешно нанесена на видео");
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static ImageWatermarkRequest imageWatermarkRequest() throws IOException {
        return new ImageWatermarkRequest(imageBytes(), watermark());
    }

    private static VideoWatermarkRequest videoWatermarkRequest() throws IOException {
        return new VideoWatermarkRequest(videoBytes(), watermark());
    }

    private static Watermark watermark() throws IOException {
        return new Watermark(watermarkBytes(), "text", CENTER);
    }

    private static byte[] imageBytes() throws IOException {
        return new ClassPathResource("image/image.jpg")
            .getInputStream()
            .readAllBytes();
    }

    private static byte[] videoBytes() throws IOException {
        return new ClassPathResource("video/video.mp4")
            .getInputStream()
            .readAllBytes();
    }

    private static byte[] watermarkBytes() throws IOException {
        return new ClassPathResource("image/watermark.png")
            .getInputStream()
            .readAllBytes();
    }
}
