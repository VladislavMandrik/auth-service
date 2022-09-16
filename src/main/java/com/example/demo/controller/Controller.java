package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Controller {

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        log.info("Hello, Spring!!!");
        return ResponseEntity.ok("Hello, Spring!!!");
    }
}
