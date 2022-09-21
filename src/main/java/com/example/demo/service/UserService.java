package com.example.demo.service;

import com.example.demo.Response;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

//                userRepository.existsByUsername(userDTO.getUsername())
//                        .filter(userExist -> !userExist)
//                        .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
//                        .map(aBoolean -> userDto)
//                        userRepository.map(UserMapper.INSTANCE::fromDTO)
//                        .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
//                        .flatMap(userRepository::save)
//                        .map(UserMapper.INSTANCE::toDTO);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public Response handleException(UserAlreadyExistsException e) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    private Mono<Boolean> isUserExist(String username) {
//        return userRepository.findByUsername(username)
//                .map(user -> true)
//                .switchIfEmpty(Mono.just(false));
//    }
}

