package com.spy.repository;

import com.spy.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, BigInteger> {
}
