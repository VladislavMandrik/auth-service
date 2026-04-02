package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface AuthController {

    @PostMapping("/login")
    Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar);
}
