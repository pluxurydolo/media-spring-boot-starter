package com.pluxurydolo.media.client;

import com.pluxurydolo.media.dto.request.ImageWatermarkRequest;
import com.pluxurydolo.media.dto.request.VideoWatermarkRequest;
import com.pluxurydolo.media.exception.ImageWatermarkException;
import com.pluxurydolo.media.exception.VideoWatermarkException;
import com.pluxurydolo.media.watermark.ImageWatermarker;
import com.pluxurydolo.media.watermark.VideoWatermarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class WatermarkClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatermarkClient.class);

    private final ImageWatermarker imageWatermarker;
    private final VideoWatermarker videoWatermarker;

    public WatermarkClient(ImageWatermarker imageWatermarker, VideoWatermarker videoWatermarker) {
        this.imageWatermarker = imageWatermarker;
        this.videoWatermarker = videoWatermarker;
    }

    public Mono<byte[]> watermarkImage(ImageWatermarkRequest request) {
        return Mono.fromCallable(() -> imageWatermarker.watermark(request))
            .doOnSuccess(_ -> LOGGER.info("sjzi [media-starter] Вотермарка успешно нанесена на картинку"))
            .onErrorResume(throwable -> {
                LOGGER.error("escw [media-starter] Произошла ошибка при нанесении вотермарки на картинку");
                return Mono.error(new ImageWatermarkException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<byte[]> watermarkVideo(VideoWatermarkRequest request) {
        return Mono.fromCallable(() -> videoWatermarker.watermark(request))
            .doOnSuccess(_ -> LOGGER.info("crcp [media-starter] Вотермарка успешно нанесена на видео"))
            .onErrorResume(throwable -> {
                LOGGER.error("xezm [media-starter] Произошла ошибка при нанесении вотермарки на видео");
                return Mono.error(new VideoWatermarkException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
