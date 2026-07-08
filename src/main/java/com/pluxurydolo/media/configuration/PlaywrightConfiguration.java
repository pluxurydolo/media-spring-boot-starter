package com.pluxurydolo.media.configuration;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class PlaywrightConfiguration {

    @Bean
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean
    public Browser browser(Playwright playwright) {
        List<String> args = asList(
            "--no-sandbox",
            "--disable-gpu",
            "--disable-dev-shm-usage"
        );

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
            .setHeadless(true)
            .setArgs(args);

        return playwright.chromium().launch(options);
    }
}
