package org.test.piano.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatsDto {

    private List<String> files;
    private List<String> stats;
}
