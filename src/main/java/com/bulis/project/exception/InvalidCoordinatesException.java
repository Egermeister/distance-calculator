package com.bulis.project.exception;

public class InvalidCoordinatesException extends RuntimeException {

    public InvalidCoordinatesException() {
        super("Invalid coordinates were provided.");
    }
}
