package com.example.demo.handlers;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserDoNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserDoNotExistsException.class)
    public ResponseEntity<Object> handleException(UserDoNotExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
