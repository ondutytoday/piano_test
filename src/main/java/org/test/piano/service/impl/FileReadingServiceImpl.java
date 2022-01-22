package org.test.piano.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.test.piano.service.FileReadingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileReadingServiceImpl implements FileReadingService {

    //вероятно можно использовать сет
    List<String> fileNames = new ArrayList<>();
    Map<String, Long> result = new ConcurrentSkipListMap<>();

    @Override
    public void readFiles(List<Path> files) {
        List<String> data = files.parallelStream()
                .map(file -> {
                    try {
                        return Files.readString(file).strip();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        log.info(data.toString());
    }

}
