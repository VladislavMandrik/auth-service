package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.exception.UserDoNotExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expirationTime;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

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
                .switchIfEmpty(Mono.error(new UserDoNotExistsException("User do not exists")));
    }

    public Mono<UserDTO> update(Long id, UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        return userRepository.findById(id)
                .flatMap(u -> {
                    if (user.getUsername() != null) u.setUsername(user.getUsername());
                    if (user.getPassword() != null) u.setPassword(user.getPassword());
                    if (user.getRole() != null) u.setRole(user.getRole());
                    return userRepository.save(u);
                })
                .map(userMapper::toDTO)
                .switchIfEmpty(Mono.error(new UserDoNotExistsException("User do not exists")));
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Collections.singleton(user.getRole()));
        return doGenerateToken(claims, user.getId());
    }

    private String doGenerateToken(Map<String, Object> claims, Long id) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(id))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Mono<User> userVerification(AuthRequest ar) {
        return findByUsername(ar.getUsername())
                .filter(userDetails -> ar.getPassword().equals(userDetails.getPassword()));
    }
}

