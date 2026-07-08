package com.pluxurydolo.media.screenshot;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pluxurydolo.media.dto.request.HTMLScreenshotRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HTMLScreenshotterTests {

    @Mock
    private Browser browser;

    @Mock
    private Resource template;

    @Mock
    private Page page;

    @Mock
    private Locator locator;

    @InjectMocks
    private HTMLScreenshotter htmlScreenshotter;

    @Test
    void testCreateScreenshot() throws IOException {
        byte[] bytes = {};
        doNothing()
            .when(page).setViewportSize(anyInt(), anyInt());
        doNothing()
            .when(page).setContent(anyString());
        doNothing()
            .when(page).waitForLoadState();
        when(browser.newPage())
            .thenReturn(page);
        when(template.getContentAsString(any()))
            .thenReturn("");
        when(page.locator(anyString()))
            .thenReturn(locator);
        when(locator.screenshot(any()))
            .thenReturn(bytes);

        byte[] result = htmlScreenshotter.createScreenshot(request());

        assertThat(result)
            .isEqualTo(bytes);
    }

    private static HTMLScreenshotRequest request() {
        return new HTMLScreenshotRequest("htmlContent");
    }
}
