package com.spy.configuration;

import com.spy.handler.EventHandler;
import com.spy.handler.UserHandler;
import com.spy.service.EventService;
import com.spy.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlersConfiguration {
    private final UserService userService;
    private final EventService eventService;

    public HandlersConfiguration(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @Bean
    public UserHandler userHandler() {
        return new UserHandler(userService);
    }

    @Bean
    public EventHandler eventHandler() {
        return new EventHandler(eventService);
    }
}
