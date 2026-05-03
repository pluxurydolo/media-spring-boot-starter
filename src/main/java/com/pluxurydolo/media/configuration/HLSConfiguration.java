package com.pluxurydolo.media.configuration;

import com.pluxurydolo.media.configurer.HLSFrameRecorderConfigurer;
import com.pluxurydolo.media.hls.HLSConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HLSConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HLSConverter hlsConverter(HLSFrameRecorderConfigurer configurer) {
        return new HLSConverter(configurer);
    }

    @Bean
    @ConditionalOnMissingBean
    public HLSFrameRecorderConfigurer hlsFrameRecorderConfigurer() {
        return new HLSFrameRecorderConfigurer();
    }
}
