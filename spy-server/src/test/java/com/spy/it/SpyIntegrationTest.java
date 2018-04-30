package com.spy.it;

import com.spy.model.CustomUserDetails;
import com.spy.model.User;
import com.spy.repository.CustomUserDetailsRepository;
import com.spy.repository.UserRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class SpyIntegrationTest {
    static final String AUTHORIZATION_HEADER = "Authorization";
    static final String USERNAME = "0991231212";
    private static final String PASSWORD = "pass12345";

    @Autowired
    private ApplicationContext context;
    @Autowired
    protected CustomUserDetailsRepository customUserDetailsRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    WebTestClient webTestClient;

    @Before
    public void init() {
        webTestClient = WebTestClient
                .bindToApplicationContext(context)
                .build();

        customUserDetailsRepository.deleteAll().block();
        userRepository.deleteAll().block();
    }

    void createUser() {
        User user = new User();
        userRepository.save(user).block();
        customUserDetailsRepository.save(new CustomUserDetails(USERNAME, passwordEncoder.encode(PASSWORD), user)).block();
    }

    String basic() {
        return basic(USERNAME, PASSWORD);
    }

    String basic(String username, String password) {
        return "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes(UTF_8));
    }
}
