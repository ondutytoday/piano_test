package org.test.piano.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PathServiceImpl implements PathService {

    @Override
    public boolean isDirectoryExist(String pathString) {
        Path path = Paths.get(pathString);
        return Files.exists(path) && Files.isDirectory(path);
    }
}
