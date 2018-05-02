package com.spy.configuration;

import com.spy.repository.CustomUserDetailsRepository;
import com.spy.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
    private final CustomUserDetailsRepository customUserDetailsRepository;

    public SecurityConfiguration(CustomUserDetailsRepository customUserDetailsRepository) {
        this.customUserDetailsRepository = customUserDetailsRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .formLogin()
                .and()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/login").permitAll()
                .pathMatchers(HttpMethod.POST, "/users").permitAll()
                .anyExchange().authenticated()
                .and().httpBasic()
                .and()
                .build();
    }

    @Bean
    public ReactiveUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(customUserDetailsRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
