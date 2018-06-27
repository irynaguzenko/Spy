package com.spy.handler;

import com.spy.dto.EventDto;
import com.spy.model.CustomUserDetails;
import com.spy.service.EventService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class EventHandler {
    private static final String ID = "id";
    private final EventService eventService;

    public EventHandler(EventService eventService) {
        this.eventService = eventService;
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return principal(serverRequest)
                .flatMap(principal -> serverRequest.bodyToMono(EventDto.class)
                        .flatMap(eventDto -> eventService.create(eventDto, principal))
                        .flatMap(event -> ServerResponse.ok().body(BodyInserters.fromObject(event)))
                        .switchIfEmpty(ServerResponse.badRequest().build()));
    }

    public Mono<ServerResponse> join(ServerRequest serverRequest) {
        String eventId = serverRequest.pathVariable(ID);
        return principal(serverRequest)
                .flatMap(principal -> eventService.join(new BigInteger(eventId), principal))
                .flatMap(event -> ServerResponse.ok().body(BodyInserters.fromObject(event)));
    }

    private Mono<CustomUserDetails> principal(ServerRequest serverRequest) {
        return serverRequest.principal()
                .map(token -> ((UsernamePasswordAuthenticationToken) token).getPrincipal())
                .map(principal -> (CustomUserDetails) principal);
    }
}
