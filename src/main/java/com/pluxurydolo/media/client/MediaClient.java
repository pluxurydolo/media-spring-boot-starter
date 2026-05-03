package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.HLSRequest;
import com.pluxurydolo.media.dto.ImageAudioMergeRequest;
import com.pluxurydolo.media.exception.HLSConverterException;
import com.pluxurydolo.media.exception.ImageAudioMergeException;
import com.pluxurydolo.media.hls.HLSConverter;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MediaClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaClient.class);

    private final ImageAudioMerger imageAudioMerger;
    private final HLSConverter hlsConverter;

    public MediaClient(ImageAudioMerger imageAudioMerger, HLSConverter hlsConverter) {
        this.imageAudioMerger = imageAudioMerger;
        this.hlsConverter = hlsConverter;
    }

    public Mono<String> mergeImageAudio(ImageAudioMergeRequest request) {
        return Mono.fromCallable(() -> imageAudioMerger.merge(request))
            .doOnSuccess(_ -> LOGGER.info("ekey [media-starter] Картинка и аудио успешно объединены"))
            .onErrorResume(throwable -> {
                LOGGER.error("doxa [media-starter] Произошла ошибка при объединении картинки и аудио");
                return Mono.error(new ImageAudioMergeException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> convertToHLS(HLSRequest request) {
        return Mono.fromCallable(() -> hlsConverter.convert(request))
            .doOnSuccess(_ -> LOGGER.info("tgaq [media-starter] HLS плейлист успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("wcqr [media-starter] Произошла ошибка при получении HLS плейлиста");
                return Mono.error(new HLSConverterException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
