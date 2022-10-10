package com.example.demo.exception;

public class UserDoesNotExistsException extends RuntimeException {
        public UserDoesNotExistsException(ExceptionMessage message) {
            super(String.valueOf(message));
        }
}
