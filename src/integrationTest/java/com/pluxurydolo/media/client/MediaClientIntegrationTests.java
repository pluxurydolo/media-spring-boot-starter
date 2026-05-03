package com.pluxurydolo.media.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.media.base.AbstractIntegrationTests;
import com.pluxurydolo.media.dto.HLSRequest;
import com.pluxurydolo.media.dto.ImageAudioMergeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

class MediaClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER = (Logger) getLogger(MediaClient.class);

    @Autowired
    private MediaClient mediaClient;

    @Test
    void testMergeImageAudio() throws IOException {
        List<ILoggingEvent> logs = listAppender().list;

        mediaClient.mergeImageAudio(imageAudioMergeRequest())
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

    @Test
    void testConvertToHLS() throws IOException {
        List<ILoggingEvent> logs = listAppender().list;

        mediaClient.convertToHLS(hlsRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                Path path = Paths.get("hls/videoName");

                assertThat(path)
                    .isDirectory();

                Path playlist = path.resolve("playlist.m3u8");
                assertThat(playlist)
                    .exists();
                assertThat(playlist)
                    .isRegularFile();
                assertThat(playlist)
                    .content()
                    .isNotEmpty();

                Path segment0 = path.resolve("segment_0.ts");
                assertThat(segment0)
                    .exists();
                assertThat(segment0)
                    .isRegularFile();
                assertThat(segment0)
                    .content()
                    .isNotEmpty();

                Path segment1 = path.resolve("segment_1.ts");
                assertThat(segment1)
                    .exists();
                assertThat(segment1)
                    .isRegularFile();
                assertThat(segment1)
                    .content()
                    .isNotEmpty();

                Path segment2 = path.resolve("segment_2.ts");
                assertThat(segment2)
                    .exists();
                assertThat(segment2)
                    .isRegularFile();
                assertThat(segment2)
                    .content()
                    .isNotEmpty();

                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("tgaq [media-starter] HLS плейлист успешно получен");
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

    private static HLSRequest hlsRequest() throws IOException {
        return new HLSRequest("videoName", videoStream(), 3.0);
    }

    private static InputStream imageStream() throws IOException {
        return new ClassPathResource("image/image.jpg")
            .getInputStream();
    }

    private static InputStream audioStream() throws IOException {
        return new ClassPathResource("audio/audio.mp3")
            .getInputStream();
    }

    private static InputStream videoStream() throws IOException {
        return new ClassPathResource("video/video.mp4")
            .getInputStream();
    }
}
