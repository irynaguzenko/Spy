package com.spy.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface CustomUserDetailsRepository extends ReactiveMongoRepository<UserDetails, BigInteger> {

    Mono<UserDetails> findByUsername(String username);
}
