package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<UserDTO> getAll() {
        return userRepository.findAll()
                .map(userMapper::toDTO);
//                .switchIfEmpty(Flux.empty());
    }

    public Mono<UserDTO> addUser(UserDTO userDTO) {
//        user.setRoleId(USER_ROLE_ID);
//        user.setPassword(encoder.encode(user.getPassword()));
        User user = userMapper.fromDTO(userDTO);
        return userRepository.save(user)
                .map(userMapper::toDTO);
    }

    public Mono<UserDTO> getById(Long id) {
//        return userRepository.findById(id)
//                .map(userMapper::toDTO);
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .switchIfEmpty(Mono.error(new UserAlreadyExistsException("user already exists")));

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

