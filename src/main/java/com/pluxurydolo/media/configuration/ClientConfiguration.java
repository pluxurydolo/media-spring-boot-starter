package com.pluxurydolo.media.configuration;

import com.pluxurydolo.media.client.MediaClient;
import com.pluxurydolo.media.hls.HLSConverter;
import com.pluxurydolo.media.merger.ImageAudioMerger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MediaClient mediaClient(ImageAudioMerger imageAudioMerger, HLSConverter hlsConverter) {
        return new MediaClient(imageAudioMerger, hlsConverter);
    }
}
