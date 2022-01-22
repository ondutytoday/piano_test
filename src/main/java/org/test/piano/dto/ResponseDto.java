package org.test.piano.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {

    private String answer;

    public ResponseDto(String answer) {
        this.answer = answer;
    }
}
