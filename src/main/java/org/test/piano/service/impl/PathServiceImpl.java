package org.test.piano.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.PathService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class PathServiceImpl implements PathService {

    @Override
    public boolean isDirectoryExist(String pathString) {
        Path path = Paths.get(pathString);
        log.info("path to get {}", path);
        return Files.isReadable(path) && Files.isDirectory(path);
    }
}
