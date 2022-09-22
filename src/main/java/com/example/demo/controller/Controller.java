package com.example.demo.controller;

import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public Flux<UserDTO> getAll() {
        return userService.getAll()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/users/{id}")
    public Mono<UserDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/registration")
    public Mono<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
}
