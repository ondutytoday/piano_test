package org.test.piano.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.PathService;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PathServiceImpl implements PathService {

    private final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:stats_\\d+.txt");

    @Override
    public boolean isDirectoryExist(String pathString) {
        Path path = Paths.get(pathString);
        log.info("Path for watching: {}", path);
        return Files.isReadable(path) && Files.isDirectory(path);
    }

    @Override
    public List<Path> getFilesFromDirectory(String pathString) throws IOException {
        List<Path> list = Files.list(Paths.get(pathString))
                .filter(Files::isRegularFile)
                .filter(file -> pathMatcher.matches(file.getFileName()))
                .collect(Collectors.toUnmodifiableList());
        log.info("Files for analyzing: {}", list);
        return list;
    }

    @Override
    public boolean isPathMatches (String pathString) {
        return pathMatcher.matches(Paths.get(pathString).getFileName());
    }
}
