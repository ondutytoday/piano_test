package org.test.piano.service;

import java.nio.file.Path;
import java.util.List;

public interface FileReadingService {

    void readFiles(List<Path> files);
}
