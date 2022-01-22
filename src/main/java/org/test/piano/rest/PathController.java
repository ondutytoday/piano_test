package org.test.piano.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.piano.dto.StatsDto;
import org.test.piano.service.SomeService;

@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class PathController {

    private final SomeService someService;

    @GetMapping(value = "/result")
    public ResponseEntity<StatsDto> getResult() {
        someService.test();
        return new ResponseEntity<>(new StatsDto(), HttpStatus.OK);
    }

    @PostMapping(value = "/path")
    public ResponseEntity setPath(@RequestBody String path) {
        return ResponseEntity.ok().build();
    }
}
