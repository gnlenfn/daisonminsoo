package com.potential.hackathon.exceptions;

import com.potential.hackathon.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.potential.hackathon.exceptions.ExceptionCode.INTERNAL_SERVER_ERROR;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessLogicException.class})
    protected ResponseEntity handleBusinessLoginException(BusinessLogicException exception) {
        return new ResponseEntity<>(new ErrorDto(
                exception.getExceptionCode().getStatus(),
                exception.getExceptionCode().getMessage()),
                HttpStatus.valueOf(exception.getExceptionCode().getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity validationException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ErrorDto(400, "invalid parameter."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception exception) {
        return new ResponseEntity(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
