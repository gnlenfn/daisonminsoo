package com.potential.hackathon.dto;

import com.potential.hackathon.exceptions.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private ExceptionCode status;
    private String message;
}
