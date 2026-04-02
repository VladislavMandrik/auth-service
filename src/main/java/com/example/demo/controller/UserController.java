package com.example.demo.controller;

import com.example.demo.model.PageSupport;
import com.example.demo.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.example.demo.model.PageSupport.DEFAULT_PAGE_SIZE;
import static com.example.demo.model.PageSupport.FIRST_PAGE_NUM;

public interface UserController {

    @GetMapping("/hello")
    ResponseEntity<String> getHello();

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    Mono<PageSupport<UserDTO>> getAll(@RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Mono<UserDTO> getUserById(@PathVariable(value = "id") Long id);

    @PostMapping("/registration")
    Mono<UserDTO> createUser(@RequestBody UserDTO userDTO);

    @PutMapping("/users/{id}")
    Mono<UserDTO> updateUser(ServerWebExchange exchange,
                             @PathVariable(value = "id") Long id,
                             @RequestBody UserDTO userDTO);
}
