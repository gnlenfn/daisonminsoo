package com.potential.hackathon.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}
