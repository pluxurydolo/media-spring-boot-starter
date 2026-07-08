package com.pluxurydolo.media.configuration;

import com.microsoft.playwright.Browser;
import com.pluxurydolo.media.screenshot.HTMLScreenshotter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class HTMLScreenshotterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HTMLScreenshotter htmlScreenshotter(
        Browser browser,
        @Value("classpath:template/screenshot.html") Resource template
    ) {
        return new HTMLScreenshotter(browser, template);
    }
}
