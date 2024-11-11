package com.example.institutionapp.exceptions;

public class InvalidUsernamePasswordException extends RuntimeException {
    public InvalidUsernamePasswordException(String message) {
        super(message);
    }
}
