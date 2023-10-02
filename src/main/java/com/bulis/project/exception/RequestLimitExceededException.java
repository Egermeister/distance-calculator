package com.bulis.project.exception;

public class RequestLimitExceededException extends RuntimeException {

    public RequestLimitExceededException() {
        super("Request limit exceeded. Please try again later.");
    }
}
