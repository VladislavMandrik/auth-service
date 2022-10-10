package com.example.demo.service;

import com.example.demo.config.JwtUtil;
import com.example.demo.exception.ExceptionMessage;
import com.example.demo.exception.UserForbiddenException;
import com.example.demo.model.*;
import com.example.demo.exception.UserDoesNotExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public Mono<PageSupport<UserDTO>> getAll(Pageable page) {
        return userRepository.findAll()
                .map(userMapper::toDTO)
                .collectList()
                .map(list -> new PageSupport<>(list
                        .stream()
                        .skip(page.getPageNumber() * page.getPageSize())
                        .limit(page.getPageSize())
                        .collect(Collectors.toList()),
                        page.getPageNumber(), page.getPageSize(), list.size()))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<UserDTO> addUser(UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        return userRepository.save(user)
                .map(userMapper::toDTO);
    }

    public Mono<UserDTO> getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .switchIfEmpty(Mono.error(new UserDoesNotExistsException(ExceptionMessage.USER_DOES_NOT_EXIST)));
    }

    public Mono<UserDTO> update(Long id, UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        return userRepository.findById(id)
                .flatMap(u -> {
                    u.setPassword(user.getPassword());
                    if (user.getRole() != null) u.setRole(user.getRole());
                    return userRepository.save(u);
                })
                .map(userMapper::toDTO)
                .switchIfEmpty(Mono.error(new UserDoesNotExistsException(ExceptionMessage.USER_DOES_NOT_EXIST)));
    }

    public Mono<UserDTO> checkRole(ServerWebExchange exchange, Long id, UserDTO userDTO) {
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
                return Mono.error(new UserForbiddenException(ExceptionMessage.YOU_CAN_NOT_CHANGE_YOUR_ROLE));
            }
            log = update(id, userDTO).log();
        } else if (!Objects.equals(id, userId) && !Objects.equals(Objects.requireNonNull(roleList).get(0), "ROLE_ADMIN")) {
            log = Mono.error(new UserForbiddenException(ExceptionMessage.YOU_CAN_CHANGE_ONLY_INFORMATION_ABOUT_YOURSELF));
        } else if (Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_ADMIN")) {
            log = update(id, userDTO).log();
        } else if (!Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_ADMIN")) {
            log = update(id, userDTO).log();
        }
        else if (Objects.equals(id, userId) && Objects.equals(roleList.get(0), "ROLE_ADMIN_R")) {
            log = update(id, userDTO).log();
        }
        return log;
    }
}