package com.potential.hackathon.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    POST_NOT_FOUND(400, "post not found");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
