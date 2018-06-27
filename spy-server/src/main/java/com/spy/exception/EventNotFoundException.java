package com.spy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventNotFoundException extends ResponseStatusException {

    public EventNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Event is not found");
    }
}
