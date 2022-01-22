package org.test.piano.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.FileWatchingService;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWatchingServiceImpl implements FileWatchingService {

    private final WatchService watchService;

    @Override
    public void startWatching(String pathString) {

    }

    private void start(String pathString) {
        try {
            log.info("set folder {} in watching service", pathString);
            Path path = Paths.get(pathString);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            log.info("start watching");
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind: {}; File affected: {}", event.kind(), event.context());
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            log.warn("watching folders was interrupted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void stopWatching() {
        log.info("stop watching");
        if (watchService != null) {
            try {
                watchService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
