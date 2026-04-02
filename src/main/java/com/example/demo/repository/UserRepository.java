package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String name);

    @Modifying
    @Query("UPDATE usr SET password = :password, role = :role WHERE id = :id")
    Mono<Integer> updatePasswordAndRole(
            @Param("id") Long id,
            @Param("password") String password,
            @Param("role") String role
    );
}