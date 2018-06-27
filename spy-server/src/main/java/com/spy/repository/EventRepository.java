package com.spy.repository;

import com.spy.model.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, BigInteger> {
}
