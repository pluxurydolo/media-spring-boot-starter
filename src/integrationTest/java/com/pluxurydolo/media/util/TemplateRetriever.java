package com.pluxurydolo.media.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TemplateRetriever {
    public static String retrieve(String path) {
        try {
            return new ClassPathResource(path)
                .getContentAsString(UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
