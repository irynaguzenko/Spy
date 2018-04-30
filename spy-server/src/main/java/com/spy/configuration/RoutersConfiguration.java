package com.spy.configuration;

import com.spy.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutersConfiguration {
    private final UserHandler userHandler;

    public RoutersConfiguration(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction() {
        return route(POST("/users"), userHandler::create)
                .andRoute(PUT("/users/{id}"), userHandler::update);
    }
}
