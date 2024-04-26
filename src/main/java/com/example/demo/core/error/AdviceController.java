package com.example.demo.core.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
    private final Logger log = LoggerFactory.getLogger(AdviceController.class);

    public static ErrorCauseDto getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        final StackTraceElement firstError = rootCause.getStackTrace()[0];
        return new ErrorCauseDto(
                rootCause.getClass().getName(),
                firstError.getMethodName(),
                firstError.getFileName(),
                firstError.getLineNumber());
    }

    private ResponseEntity<ErrorDto> handleException(Throwable throwable, HttpStatusCode httpCode) {
        log.error("{}", throwable.getMessage());
        throwable.printStackTrace();
        final ErrorDto errorDto = new ErrorDto(throwable.getMessage(), AdviceController.getRootCause(throwable));
        return new ResponseEntity<>(errorDto, httpCode);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleNotFoundException(Throwable throwable) {
        return handleException(throwable, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(Throwable throwable) {
        return handleException(throwable, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleAnyException(Throwable throwable) {
        return handleException(throwable, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
