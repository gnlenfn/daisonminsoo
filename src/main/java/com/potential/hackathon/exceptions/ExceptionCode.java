package com.potential.hackathon.exceptions;

import lombok.Getter;

public enum ExceptionCode {

    POST_NOT_FOUND(400, "post not found");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
