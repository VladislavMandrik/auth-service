package com.example.demo.controller;

import com.example.demo.model.PageSupport;
import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.example.demo.model.PageSupport.DEFAULT_PAGE_SIZE;
import static com.example.demo.model.PageSupport.FIRST_PAGE_NUM;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        log.info("Hello, Spring!!!");
        return ResponseEntity.ok("Hello, Spring!!!");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<PageSupport<UserDTO>> getAll(@RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                             @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        return userService.getAll(PageRequest.of(page, size)).log()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<UserDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getById(id).log();
    }

    @PostMapping("/registration")
    public Mono<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO).log();
    }

    @PutMapping("/users/{id}")
    public Mono<UserDTO> updateUser(@PathVariable(value = "id") Long id,
                                    @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO).log();
    }
}
