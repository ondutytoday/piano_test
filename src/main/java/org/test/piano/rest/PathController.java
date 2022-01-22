package org.test.piano.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.piano.dto.Answer;
import org.test.piano.dto.ResponseDto;
import org.test.piano.dto.StatsDto;
import org.test.piano.service.FileReadingService;
import org.test.piano.service.FileWatchingService;
import org.test.piano.service.PathService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;
    private final FileWatchingService fileWatchingService;
    private final FileReadingService fileReadingService;

    @GetMapping(value = "/result")
    public ResponseEntity<StatsDto> getResult() {
        return new ResponseEntity<>(new StatsDto(), HttpStatus.OK);
    }

    @PostMapping(value = "/path")
    public ResponseEntity<ResponseDto> setPath(@RequestBody String path) {
        if (path.isBlank()) {
            return new ResponseEntity<>(new ResponseDto(Answer.EMPTY.getValue()), HttpStatus.BAD_REQUEST);
        }
        boolean isExist = pathService.isDirectoryExist(path);
        if(!isExist) {
            return new ResponseEntity<>(new ResponseDto(Answer.NOT_FOUND.getValue()), HttpStatus.BAD_REQUEST);
        }
        //нужно передать в экзекьютор, чтобы крутилось в отдельном треде
        //предусмотреть отключение предыдущего вочера
        //fileWatchingService.startWatching(path);
        try {
            List<Path> pathss = pathService.getFilesFromDirectory(path);
            fileReadingService.readFiles(pathss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ResponseDto(Answer.SUCCESS.getValue()), HttpStatus.OK);
    }
}
