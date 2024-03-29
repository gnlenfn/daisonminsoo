package com.potential.hackathon.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private final HttpStatus code;
    public NotFoundException(HttpStatus code) {
        this.code = code;
    }

    public NotFoundException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }
}
