package com.spy.service;

import com.spy.dto.CreateUserDto;
import com.spy.dto.UpdateUserDto;
import com.spy.exception.UserAlreadyExistsException;
import com.spy.exception.UserNotFoundException;
import com.spy.exception.UserNotSavedException;
import com.spy.model.CustomUserDetails;
import com.spy.model.User;
import com.spy.repository.CustomUserDetailsRepository;
import com.spy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Validated
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       CustomUserDetailsRepository customUserDetailsRepository,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsRepository = customUserDetailsRepository;
        this.userRepository = userRepository;
    }

    public Mono<CustomUserDetails> create(@Valid CreateUserDto userDto) {
        String username = userDto.getUsername();
        return customUserDetailsRepository.findByUsername(username)
                .flatMap(userDetails -> Mono.<CustomUserDetails>error(new UserAlreadyExistsException()))
                .switchIfEmpty(createUser(userDto));
    }

    private Mono<CustomUserDetails> createUser(CreateUserDto userDto) {
        return userRepository.save(new User())
                .flatMap(user -> {
                    String encodedPassword = passwordEncoder.encode(userDto.getPassword());
                    CustomUserDetails userDetails = new CustomUserDetails(userDto.getUsername(), encodedPassword, user);
                    return customUserDetailsRepository.save(userDetails);
                })
                .switchIfEmpty(Mono.error(new UserNotSavedException()));
    }

    @PreAuthorize("principal.id == #userDetailsId")
    public Mono<User> update(BigInteger userDetailsId, @Valid UpdateUserDto updateUserDto) {
        return customUserDetailsRepository.findById(userDetailsId)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(userDetails -> userRepository.save(user(updateUserDto, userDetails)))
                .doOnNext(user -> log.info("User is updated to: {}", user));
    }

    private User user(UpdateUserDto updateUserDto, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        user.setGivenName(updateUserDto.getGivenName());
        user.setFamilyName(updateUserDto.getFamilyName());
        user.setBirthDate(updateUserDto.getBirthDate());
        user.setCity(updateUserDto.getCity());
        return user;
    }
}
