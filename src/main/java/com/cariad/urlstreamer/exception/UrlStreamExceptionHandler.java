package com.cariad.urlstreamer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UrlStreamExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UrlStreamExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAllOtherExceptions(Exception ex) {
        logger.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        return new ExceptionResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
    }

    @ExceptionHandler(InputSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInputSizeLimitExceededException(InputSizeLimitExceededException ex) {
        logger.error(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        return new ExceptionResponse("BAD_REQUEST", ex.getMessage());
    }
}
