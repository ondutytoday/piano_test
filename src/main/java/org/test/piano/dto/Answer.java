package org.test.piano.dto;

public enum Answer {

    SUCCESS("Директория успешно установлена"),
    NOT_FOUND("Директория не найдена. Возможно, доступ к ней запрещен или такая папка не существует"),
    EMPTY("Вы ввели пустое значение");

    private String value;

    Answer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
