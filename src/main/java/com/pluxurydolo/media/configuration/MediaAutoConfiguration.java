package com.pluxurydolo.media.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
    ClientConfiguration.class,
    MergerConfiguration.class,
    HLSConfiguration.class
})
public class MediaAutoConfiguration {
}
