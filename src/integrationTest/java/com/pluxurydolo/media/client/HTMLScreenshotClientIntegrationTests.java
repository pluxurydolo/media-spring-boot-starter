package com.pluxurydolo.media.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.media.base.AbstractIntegrationTests;
import com.pluxurydolo.media.dto.request.HTMLScreenshotRequest;
import com.pluxurydolo.media.util.BytesSaver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static com.pluxurydolo.media.util.TemplateRetriever.retrieve;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class HTMLScreenshotClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER = (Logger) getLogger(HTMLScreenshotClient.class);

    @Autowired
    private HTMLScreenshotClient htmlScreenshotClient;

    @Test
    void testCreateScreenshot() {
        List<ILoggingEvent> logs = listAppender().list;

        byte[] result = htmlScreenshotClient.createScreenshot(htmlScreenshotRequest())
            .block();
        Path path = BytesSaver.saveImage(result);
        File file = path.toFile();

        assertThat(file)
            .exists()
            .hasSize(12009L);

        assertThat(logs)
            .hasSize(1);

        assertThat(logs.getFirst().getFormattedMessage())
            .isEqualTo("uvvn [media-starter] Скриншот из HTML кода успешно создан");
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static HTMLScreenshotRequest htmlScreenshotRequest() {
        return new HTMLScreenshotRequest(retrieve("template/content.html"));
    }
}
