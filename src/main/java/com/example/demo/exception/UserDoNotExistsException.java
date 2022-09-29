package com.example.demo.exception;

public class UserDoNotExistsException extends RuntimeException {
        public UserDoNotExistsException(String message) {
            super(message);
        }
}
