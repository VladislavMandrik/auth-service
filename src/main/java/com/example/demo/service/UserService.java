package com.example.demo.service;

import com.example.demo.PageSupport;
import com.example.demo.exception.UserDoNotExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        user.setRole_id(1);
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
                .map((u) -> {
                   if (user.getUsername() != null) u.setUsername(user.getUsername());
                   if (user.getPassword() != null) u.setPassword(user.getPassword());
                    return u;
                }).flatMap(userRepository::save)
                .map(userMapper::toDTO);

    }
}

