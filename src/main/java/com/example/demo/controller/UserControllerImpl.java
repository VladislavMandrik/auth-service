package com.example.demo.controller;

import com.example.demo.model.PageSupport;
import com.example.demo.model.UserDTO;
import com.example.demo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.example.demo.model.PageSupport.DEFAULT_PAGE_SIZE;
import static com.example.demo.model.PageSupport.FIRST_PAGE_NUM;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;

    public ResponseEntity<String> getHello() {
        log.info("Hello, Spring!!!");
        return ResponseEntity.ok("Hello, Spring!!!");
    }

    public Mono<PageSupport<UserDTO>> getAll(@RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                             @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        return userService.getAll(PageRequest.of(page, size)).log()
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<UserDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getById(id).log();
    }

    public Mono<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO).log();
    }

    public Mono<UserDTO> updateUser(ServerWebExchange exchange,
                                    @PathVariable(value = "id") final Long id,
                                    @RequestBody final UserDTO userDTO) {
        return userService.checkRole(exchange, id, userDTO);
    }
}
