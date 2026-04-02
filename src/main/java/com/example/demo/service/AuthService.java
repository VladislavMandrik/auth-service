package com.example.demo.service;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AuthService {
    Mono<User> findByUsername(String username);
    String generateToken(User user);
    String doGenerateToken(Map<String, Object> claims, Long id);
    Mono<ResponseEntity<AuthResponse>> userVerification(AuthRequest ar);

}
