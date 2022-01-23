package org.test.piano.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StatsDto {

    private List<String> files;
    private List<String> stats;
}
