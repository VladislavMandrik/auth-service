package com.example.demo.service;

import com.example.demo.Response;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> getAll() {
        return userRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono addUser(User user) {
//        user.setRoleId(USER_ROLE_ID);
//        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.findByUsername(user.getUsername()).flatMap((e) ->
                Mono.error(new UserAlreadyExistsException(user.getUsername()))
        ).switchIfEmpty(
                Mono.defer(() -> userRepository.save(user))
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public Response handleException(UserAlreadyExistsException e) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

