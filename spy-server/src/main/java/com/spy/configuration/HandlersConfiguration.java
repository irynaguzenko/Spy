package com.spy.configuration;

import com.spy.handler.UserHandler;
import com.spy.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlersConfiguration {
    private final UserService userService;

    public HandlersConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserHandler userHandler() {
        return new UserHandler(userService);
    }
}
