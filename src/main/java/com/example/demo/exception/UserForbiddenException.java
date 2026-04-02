package com.example.demo.exception;

public class UserForbiddenException extends RuntimeException {
    public UserForbiddenException(ExceptionMessage message) {
        super(String.valueOf(message));
    }
}

