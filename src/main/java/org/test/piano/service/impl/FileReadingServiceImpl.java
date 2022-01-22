package org.test.piano.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.FileReadingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileReadingServiceImpl implements FileReadingService {

    //вероятно можно использовать сет
    Set<Path> fileNames = new HashSet<>();
    Map<String, Long> result = new ConcurrentSkipListMap<>();

    @Override
    public void readFiles(List<Path> files) {
        fileNames.addAll(files
                .stream()
                .map(Path::getFileName)
                .collect(Collectors.toSet()));

        files.parallelStream().forEach(this::scanFile);
        log.info(fileNames.toString());
        log.info(result.toString());
    }

    private void scanFile(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pair pair = getPair(line);
                result.merge(pair.key, pair.value, Long::sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pair getPair(String line) {
        //нужна валидация стринги
        String[] values = line.split(",");
        Pair pair = new Pair(values[0], Long.parseLong(values[1]));
        return pair;
    }

    @Getter
    @AllArgsConstructor
    private class Pair {
        private String key;
        private Long value;
    }
}
