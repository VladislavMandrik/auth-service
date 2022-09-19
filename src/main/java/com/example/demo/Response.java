package com.example.demo;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class Response {

    private String message;

    private HttpStatus httpStatus;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
