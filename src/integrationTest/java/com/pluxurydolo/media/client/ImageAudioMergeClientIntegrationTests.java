package com.pluxurydolo.media.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.media.base.AbstractIntegrationTests;
import com.pluxurydolo.media.dto.ImageAudioMergeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

class ImageAudioMergeClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER = (Logger) getLogger(ImageAudioMergeClient.class);

    @Autowired
    private ImageAudioMergeClient imageAudioMergeClient;

    @Test
    void testMergeImageAudio() throws IOException {
        List<ILoggingEvent> logs = listAppender().list;

        imageAudioMergeClient.mergeImageAudio(imageAudioMergeRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                File file = Paths.get("videos/videoName.mp4")
                    .toFile();

                assertThat(file)
                    .exists()
                    .hasSize(293417L);

                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("ekey [media-starter] Картинка и аудио успешно объединены");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static ImageAudioMergeRequest imageAudioMergeRequest() throws IOException {
        return new ImageAudioMergeRequest("videoName", imageStream(), audioStream());
    }

    private static InputStream imageStream() throws IOException {
        return new ClassPathResource("image/image.jpg")
            .getInputStream();
    }

    private static InputStream audioStream() throws IOException {
        return new ClassPathResource("audio/audio.mp3")
            .getInputStream();
    }
}
