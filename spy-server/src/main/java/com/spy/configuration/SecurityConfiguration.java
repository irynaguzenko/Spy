package com.spy.configuration;

import com.spy.repository.CustomUserDetailsRepository;
import com.spy.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

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
                .authenticationFailureHandler(authenticationFailureHandler())
                .authenticationSuccessHandler(serverAuthenticationSuccessHandler())
                .authenticationEntryPoint(authEntryPoint())
                .requiresAuthenticationMatcher(loginMatcher())
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
    public ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return (webFilterExchange, authentication) -> {
            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        };
    }

    @Bean
    public ServerAuthenticationEntryPointFailureHandler authenticationFailureHandler() {
        return new ServerAuthenticationEntryPointFailureHandler(authEntryPoint());
    }

    @Bean
    public ServerAuthenticationEntryPoint authEntryPoint() {
        return (exchange, e) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("ERROR_MESSAGE", e.getMessage());
            return response.setComplete();
        };
    }

    private PathPatternParserServerWebExchangeMatcher loginMatcher() {
        return new PathPatternParserServerWebExchangeMatcher("/login", HttpMethod.POST);
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
