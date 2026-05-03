package com.pluxurydolo.media.configuration;

import com.pluxurydolo.media.configurer.ImageAudioMergerFrameRecorderConfigurer;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MergerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ImageAudioMerger imageAudioMerger(ImageAudioMergerFrameRecorderConfigurer configurer) {
        return new ImageAudioMerger(configurer);
    }

    @Bean
    @ConditionalOnMissingBean
    public ImageAudioMergerFrameRecorderConfigurer imageAudioMergerFrameRecorderConfigurer() {
        return new ImageAudioMergerFrameRecorderConfigurer();
    }
}
