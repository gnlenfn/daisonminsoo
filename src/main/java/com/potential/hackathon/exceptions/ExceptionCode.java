package com.potential.hackathon.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // 400
    INVALID_PARAMETER(400, "wrong parameter"),

    // 404
    POST_NOT_FOUND(404, "post id not found"),
    IMAGE_NOT_FOUND(404, "wrong image path"),
    COMMENT_NOT_FOUND(404, "comment id not found"),

    // 500
    INTERNAL_SERVER_ERROR(500, "internal server error");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
