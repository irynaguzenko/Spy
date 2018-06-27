package com.spy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIsAlreadyParticipantException extends ResponseStatusException {

    public UserIsAlreadyParticipantException() {
        super(HttpStatus.CONFLICT, "User is already a participant of the event");
    }
}
