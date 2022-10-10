package com.example.demo.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(ExceptionMessage message) {
        super(String.valueOf(message));
    }
}
