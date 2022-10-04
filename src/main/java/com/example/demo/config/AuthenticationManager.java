package com.example.demo.config;

import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthenticationManager(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        Long id = jwtUtil.getIdFromToken(authToken);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        userService.getById(id)
                .map(u -> authorityList.add(new SimpleGrantedAuthority(u.getRole())));
        System.out.println(authorityList);
        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get(null, null);
                    return new UsernamePasswordAuthenticationToken(
                            id,
                            null,
                            authorityList
                    );
                });
    }
}

