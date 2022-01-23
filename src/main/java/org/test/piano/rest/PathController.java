package org.test.piano.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.piano.dto.Answer;
import org.test.piano.dto.PathEventArgs;
import org.test.piano.dto.ResponseDto;
import org.test.piano.dto.StatsDto;
import org.test.piano.service.FileReadingService;
import org.test.piano.service.PathService;


@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;
    private final FileReadingService fileReadingService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(value = "/result")
    public ResponseEntity<StatsDto> getResult() {
        StatsDto statsDto = fileReadingService.getStats();
        return new ResponseEntity<>(statsDto, HttpStatus.OK);
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
        applicationEventPublisher.publishEvent(new PathEventArgs(path));
        return new ResponseEntity<>(new ResponseDto(Answer.SUCCESS.getValue()), HttpStatus.OK);
    }
}
