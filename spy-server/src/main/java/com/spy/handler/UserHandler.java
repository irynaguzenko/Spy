package com.spy.handler;

import com.spy.dto.CreateUserDto;
import com.spy.dto.UpdateUserDto;
import com.spy.service.UserService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class UserHandler {
    private static final String ID = "id";
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<CreateUserDto> userDto = serverRequest.bodyToMono(CreateUserDto.class);
        return userDto
                .flatMap(user -> userService.create(user.getUsername(), user.getPassword()))
                .flatMap(userDetails -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String userDetailsId = serverRequest.pathVariable(ID);
        return serverRequest.bodyToMono(UpdateUserDto.class)
                .flatMap(updateUserDto -> userService.update(new BigInteger(userDetailsId), updateUserDto))
                .flatMap(user -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.badRequest().build());
    }
}
