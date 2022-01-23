package org.test.piano.service;

import org.test.piano.dto.StatsDto;

import java.nio.file.Path;
import java.util.List;

public interface FileReadingService {

    void readFiles(List<Path> files);

    StatsDto getStats();

    void clearPreviousResult();
}
