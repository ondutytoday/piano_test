package org.test.piano.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.test.piano.service.FileReadingService;
import org.test.piano.service.FileWatchingService;
import org.test.piano.service.PathService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWatchingServiceImpl implements FileWatchingService {

    private WatchService watchService;
    private Map<WatchKey, Path> keys;
    private final PathService pathService;
    private final FileReadingService fileReadingService;


    @PostConstruct
    private void init() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Async
    @Override
    public void startWatching(String pathString) throws IOException {
        log.info("Set folder {} in watching service", pathString);
        Path path = Paths.get(pathString);
        register(path);
        processEvents();
    }

    private void register(Path path) throws IOException {
        WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        keys = Collections.singletonMap(key, path);
    }

    private void processEvents() {
        try {
            log.info("Start watching");
            WatchKey key;
            while ((key = watchService.take()) != null && keys.containsKey(key)) {
                Path path = keys.get(key);
                if (path != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        log.info("Event kind: {}; File affected: {}", event.kind(), event.context());
                        String newFileName = event.context().toString();
                        if (pathService.isPathMatches(newFileName)) {
                            fileReadingService.readFiles(List.of(path.resolve(newFileName)));
                        }
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            log.warn("Watching was interrupted");
        }
    }

    @PreDestroy
    private void stopWatching() throws IOException {
        log.info("stop watching");
        if (watchService != null) {
            watchService.close();
        }
    }
}
