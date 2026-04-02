package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import com.example.demo.service.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthControllerImpl implements AuthController {
    private final AuthServiceImpl authService;

    public AuthControllerImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }

    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return authService.userVerification(ar);
    }
}

