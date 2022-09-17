package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class Controller {

    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        log.info("Hello, Spring!!!");
        return ResponseEntity.ok("Hello, Spring!!!");
    }

    @GetMapping("/users")
    public Flux<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/registration")
    public Mono createUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
