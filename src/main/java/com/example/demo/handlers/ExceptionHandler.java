package com.example.demo.handlers;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistsException;
import com.example.demo.exception.UserForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({UserAlreadyExistsException.class, UserDoesNotExistsException.class})
    public ResponseEntity<ErrorMessage> handleException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage(), new Date().toString()), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleException(UserForbiddenException e) {
        return new ResponseEntity<>(
                new ErrorMessage(e.getMessage(), new Date().toString()), HttpStatus.FORBIDDEN);
    }
}
