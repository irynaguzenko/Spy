package com.spy.service;

import com.spy.repository.CustomUserDetailsRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final CustomUserDetailsRepository customUserDetailsRepository;

    public CustomUserDetailsService(CustomUserDetailsRepository customUserDetailsRepository) {
        this.customUserDetailsRepository = customUserDetailsRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return customUserDetailsRepository.findByUsername(username);
    }
}
