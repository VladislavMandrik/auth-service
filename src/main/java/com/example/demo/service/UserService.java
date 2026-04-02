package com.example.demo.service;

import com.example.demo.model.PageSupport;
import com.example.demo.model.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<PageSupport<UserDTO>> getAll(Pageable page);

    Mono<UserDTO> addUser(UserDTO userDTO);

    Mono<UserDTO> getById(Long id);

    Mono<UserDTO> update(Long id, UserDTO userDTO);

    Mono<UserDTO> checkRole(ServerWebExchange exchange, Long id, UserDTO userDTO);
}
