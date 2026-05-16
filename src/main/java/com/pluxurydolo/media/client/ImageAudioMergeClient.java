package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.request.ImageAudioMergeRequest;
import com.pluxurydolo.media.exception.ImageAudioMergeException;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ImageAudioMergeClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageAudioMergeClient.class);

    private final ImageAudioMerger imageAudioMerger;

    public ImageAudioMergeClient(ImageAudioMerger imageAudioMerger) {
        this.imageAudioMerger = imageAudioMerger;
    }

    public Mono<byte[]> mergeImageAudio(ImageAudioMergeRequest request) {
        return Mono.fromCallable(() -> imageAudioMerger.merge(request))
            .doOnSuccess(_ -> LOGGER.info("ekey [media-starter] Картинка и аудио успешно объединены"))
            .onErrorResume(throwable -> {
                LOGGER.error("doxa [media-starter] Произошла ошибка при объединении картинки и аудио");
                return Mono.error(new ImageAudioMergeException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
