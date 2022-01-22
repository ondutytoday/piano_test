package org.test.piano.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

@Configuration
public class WatchingServiceConfig {

    @Bean
    public WatchService watchService() {
        WatchService watchService = null;
        try {
            watchService=  FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return watchService;
    }
}
