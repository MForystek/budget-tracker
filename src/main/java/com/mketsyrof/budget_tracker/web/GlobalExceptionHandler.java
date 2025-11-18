package com.mketsyrof.budget_tracker.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementException(Exception e, WebRequest request) {
        logException(e);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        return createResponseEntity(pd, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(Exception e, WebRequest request) {
        logException(e);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        return createResponseEntity(pd, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGeneralException(Exception e, WebRequest request) {
        logException(e);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return createResponseEntity(pd, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private void logException(Exception e) {
        log.error("Caught exception", e);
    }
}
