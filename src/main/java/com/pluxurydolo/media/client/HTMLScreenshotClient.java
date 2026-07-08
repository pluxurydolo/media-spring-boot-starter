package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.request.HTMLScreenshotRequest;
import com.pluxurydolo.media.exception.HTMLScreenshotException;
import com.pluxurydolo.media.screenshot.HTMLScreenshotter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class HTMLScreenshotClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTMLScreenshotClient.class);

    private final HTMLScreenshotter htmlScreenshotter;

    public HTMLScreenshotClient(HTMLScreenshotter htmlScreenshotter) {
        this.htmlScreenshotter = htmlScreenshotter;
    }

    public Mono<byte[]> createScreenshot(HTMLScreenshotRequest request) {
        return Mono.fromCallable(() -> htmlScreenshotter.createScreenshot(request))
            .doOnSuccess(_ -> LOGGER.info("uvvn [media-starter] Скриншот из HTML кода успешно создан"))
            .onErrorResume(throwable -> {
                LOGGER.error("gaxg [media-starter] Произошла ошибка при создании скриншота из HTML кода");
                return Mono.error(new HTMLScreenshotException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
