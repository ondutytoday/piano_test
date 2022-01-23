package org.test.piano.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.test.piano.dto.PathEventArgs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PathEventListener {

    private final FileWatchingService fileWatchingService;
    private final FileReadingService fileReadingService;
    private final PathService pathService;

    @EventListener
    public void onPathAdd(PathEventArgs event) throws IOException {
        fileReadingService.clearPreviousResult();
        fileWatchingService.startWatching(event.getPath());
        List<Path> filesToScan = pathService.getFilesFromDirectory(event.getPath());
        fileReadingService.readFiles(filesToScan);
    }
}
