package com.pluxurydolo.media.configuration;

import com.pluxurydolo.media.configurer.VideoWatermarkFrameRecorderConfigurer;
import com.pluxurydolo.media.watermark.ImageWatermarker;
import com.pluxurydolo.media.watermark.VideoWatermarker;
import com.pluxurydolo.media.watermark.step.WatermarkDetailsRetriever;
import com.pluxurydolo.media.watermark.step.WatermarkImageAdder;
import com.pluxurydolo.media.watermark.step.WatermarkScaler;
import com.pluxurydolo.media.watermark.step.WatermarkTextAdder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatermarkerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ImageWatermarker imageWatermarker(
        WatermarkDetailsRetriever watermarkDetailsRetriever,
        WatermarkScaler watermarkScaler,
        WatermarkImageAdder watermarkImageAdder,
        WatermarkTextAdder watermarkTextAdder
    ) {
        return new ImageWatermarker(watermarkDetailsRetriever, watermarkScaler, watermarkImageAdder, watermarkTextAdder);
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoWatermarker videoWatermarker(
        VideoWatermarkFrameRecorderConfigurer configurer,
        WatermarkScaler watermarkScaler,
        WatermarkImageAdder watermarkImageAdder,
        WatermarkTextAdder watermarkTextAdder
    ) {
        return new VideoWatermarker(configurer, watermarkScaler, watermarkImageAdder, watermarkTextAdder);
    }

    @Bean
    @ConditionalOnMissingBean
    public WatermarkDetailsRetriever watermarkDetailsRetriever() {
        return new WatermarkDetailsRetriever();
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoWatermarkFrameRecorderConfigurer videoWatermarkFrameRecorderConfigurer() {
        return new VideoWatermarkFrameRecorderConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public WatermarkScaler watermarkScaler() {
        return new WatermarkScaler();
    }

    @Bean
    @ConditionalOnMissingBean
    public WatermarkImageAdder watermarkImageAdder() {
        return new WatermarkImageAdder();
    }

    @Bean
    @ConditionalOnMissingBean
    public WatermarkTextAdder watermarkTextAdder() {
        return new WatermarkTextAdder();
    }
}
