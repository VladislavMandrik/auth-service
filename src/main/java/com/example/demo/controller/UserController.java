package com.example.demo.controller;

import com.example.demo.config.JwtUtil;
import com.example.demo.exception.UserForbiddenException;
import com.example.demo.model.PageSupport;
import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

import static com.example.demo.model.PageSupport.DEFAULT_PAGE_SIZE;
import static com.example.demo.model.PageSupport.FIRST_PAGE_NUM;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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
    public Mono<UserDTO> updateUser(ServerWebExchange exchange,
                                    @PathVariable(value = "id") Long id,
                                    @RequestBody UserDTO userDTO) {
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        Long userId = null;
        List<String> roleList = null;
        Mono<UserDTO> log = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            userId = jwtUtil.getIdFromToken(authToken);
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            roleList = claims.get("role", List.class);
        }

        if (Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_USER")) {
            if (userDTO.getRole() != null) {
                return Mono.error(new UserForbiddenException("You can't change your role"));
            }
            log = userService.update(id, userDTO).log();
        } else if (!Objects.equals(id, userId) && Objects.equals(Objects.requireNonNull(roleList).get(0), "ROLE_USER")) {
            log = Mono.error(new UserForbiddenException("You can change only information about yourself"));
        } else if (Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_ADMIN")) {
            log = userService.update(id, userDTO).log();
        } else if (!Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_ADMIN")) {
            log = userService.update(id, userDTO).log();
        }

        return log;
    }
}
