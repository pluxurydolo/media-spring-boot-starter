package com.pluxurydolo.media.configuration;

import com.pluxurydolo.media.client.ImageAudioMergeClient;
import com.pluxurydolo.media.client.WatermarkClient;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import com.pluxurydolo.media.watermark.ImageWatermarker;
import com.pluxurydolo.media.watermark.VideoWatermarker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ImageAudioMergeClient imageAudioMergeClient(ImageAudioMerger imageAudioMerger) {
        return new ImageAudioMergeClient(imageAudioMerger);
    }

    @Bean
    @ConditionalOnMissingBean
    public WatermarkClient watermarkClient(ImageWatermarker imageWatermarker, VideoWatermarker videoWatermarker) {
        return new WatermarkClient(imageWatermarker, videoWatermarker);
    }
}
