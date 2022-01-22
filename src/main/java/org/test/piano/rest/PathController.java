package org.test.piano.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.piano.dto.Answer;
import org.test.piano.dto.ResponseDto;
import org.test.piano.dto.StatsDto;
import org.test.piano.service.PathService;

@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

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
        //здесь отправляю директорию в слушатель
        return new ResponseEntity<>(new ResponseDto(Answer.SUCCESS.getValue()), HttpStatus.OK);
    }
}
