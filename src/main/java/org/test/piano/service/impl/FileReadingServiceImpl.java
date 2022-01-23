package org.test.piano.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.dto.StatsDto;
import org.test.piano.service.FileReadingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileReadingServiceImpl implements FileReadingService {

    Set<Path> fileNames = new TreeSet<>();
    Map<String, Long> result = new ConcurrentSkipListMap<>();

    @Override
    public void readFiles(List<Path> files) {

        List<Path> filesToAdd = excludeSameFiles(files);
        fileNames.addAll(filesToAdd
                .stream()
                .map(Path::getFileName)
                .collect(Collectors.toSet()));

        filesToAdd.parallelStream().forEach(this::scanFile);

        //вывод логов в консоль для первой части задания
        log.info("Files: {}", fileNames
                .stream()
                .map(Path::toString)
                .collect(Collectors.joining(", ")));

        log.info("Stats:\n{}", result
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "," + entry.getValue() + ",")
                .collect(Collectors.joining("\n")));
    }

    @Override
    public StatsDto getStats() {
        List<String> files = fileNames
                .stream()
                .map(Path::toString)
                .collect(Collectors.toList());
        List<String> stats = result
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "," + entry.getValue() + ",")
                .collect(Collectors.toList());
        return new StatsDto(files, stats);
    }

    private List<Path> excludeSameFiles(List<Path> files) {
        return files.stream()
                .filter(file -> !fileNames.contains(file.getFileName()))
                .collect(Collectors.toList());
    }

    private void scanFile(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValid(line)) {
                    String[] pair = line.split(",");
                    result.merge(pair[0], Long.parseLong(pair[1]), Long::sum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String line) {
        return line.matches("\\w+,\\d+,");
    }
}
