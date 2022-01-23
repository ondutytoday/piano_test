package org.test.piano.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.FileReadingService;
import org.test.piano.service.FileWatchingService;
import org.test.piano.service.PathService;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWatchingServiceImpl implements FileWatchingService {

    private final WatchService watchService;
    private final PathService pathService;
    private final FileReadingService fileReadingService;

    @Override
    public void startWatching(String pathString) {
        start(pathString);
    }

    private void start(String pathString) {
        try {
            log.info("Set folder {} in watching service", pathString);
            Path path = Paths.get(pathString);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            log.info("Start watching");
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind: {}; File affected: {}", event.kind(), event.context());
                    String newFileName = event.context().toString();
                    if (pathService.isPathMatches(newFileName)) {
                        fileReadingService.readFiles(List.of(Paths.get(newFileName)));
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            log.warn("Watching was interrupted");
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
