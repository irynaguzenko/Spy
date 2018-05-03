package com.spy.configuration;

import com.spy.repository.CustomUserDetailsRepository;
import com.spy.repository.EventRepository;
import com.spy.repository.UserRepository;
import com.spy.service.EventService;
import com.spy.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServicesConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public ServicesConfiguration(PasswordEncoder passwordEncoder,
                                 CustomUserDetailsRepository customUserDetailsRepository,
                                 UserRepository userRepository,
                                 EventRepository eventRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsRepository = customUserDetailsRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(passwordEncoder, customUserDetailsRepository, userRepository);
    }

    @Bean
    public EventService eventService() {
        return new EventService(eventRepository);
    }
}
