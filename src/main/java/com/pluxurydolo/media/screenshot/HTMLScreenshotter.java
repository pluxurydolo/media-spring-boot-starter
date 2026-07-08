package com.pluxurydolo.media.screenshot;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pluxurydolo.media.dto.request.HTMLScreenshotRequest;
import com.pluxurydolo.media.exception.CreateScreenshotException;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static com.microsoft.playwright.options.ScreenshotType.PNG;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HTMLScreenshotter {
    private final Browser browser;
    private final Resource template;

    public HTMLScreenshotter(Browser browser, Resource template) {
        this.browser = browser;
        this.template = template;
    }

    public byte[] createScreenshot(HTMLScreenshotRequest request) {
        String htmlContent = request.htmlContent();

        try (Page page = browser.newPage()) {
            String styledHTML = template.getContentAsString(UTF_8)
                .replace("{content}", htmlContent);

            page.setViewportSize(1920, 1080);
            page.setContent(styledHTML);
            page.waitForLoadState();

            Locator questionBlock = page.locator(".content");

            return questionBlock.screenshot(options());
        } catch (IOException exception) {
            throw new CreateScreenshotException(exception);
        }
    }

    private static Locator.ScreenshotOptions options() {
        return new Locator.ScreenshotOptions()
            .setType(PNG)
            .setOmitBackground(false);
    }
}
