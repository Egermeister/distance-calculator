package com.bulis.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidCoordinatesException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCoordinatesException(InvalidCoordinatesException exception) {
        log.error("Error Details: ", exception);
        var status = HttpStatus.BAD_REQUEST;
        var errorResponse = buildErrorResponse(exception.getMessage(), status);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRequestLimitExceededException(RequestLimitExceededException exception) {
        log.error("Error Details: ", exception);
        var status = HttpStatus.TOO_MANY_REQUESTS;
        var errorResponse = buildErrorResponse(exception.getMessage(), status);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(String message, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .status(httpStatus.value())
                .timestamp(Instant.now().toString())
                .build();
    }
}
