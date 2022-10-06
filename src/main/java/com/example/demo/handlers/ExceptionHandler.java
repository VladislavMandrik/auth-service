package com.example.demo.handlers;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserDoNotExistsException;
import com.example.demo.exception.UserForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage(), 409, "The user already exists"), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserDoNotExistsException.class)
    public ResponseEntity<ErrorMessage> handleException(UserDoNotExistsException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage(), 409, "The user do not exists"), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleException(UserForbiddenException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage(), 403, "User forbidden"), HttpStatus.FORBIDDEN);
    }
}
