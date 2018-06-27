package com.spy.it;

import com.spy.dto.CreateUserDto;
import com.spy.dto.UpdateUserDto;
import com.spy.model.CustomUserDetails;
import com.spy.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagementTest extends SpyIntegrationTest {
    private static final String NEW_FAMILY_NAME = "New FamilyName";
    private static final String NEW_GIVEN_NAME = "New GivenName";
    private static final LocalDate NEW_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private static final String NEW_CITY = "New City";
    private static final String USER_PHONE = "0891112233";
    private static final String USER_PASSWORD = "Password987";


    @Test
    public void shouldCreateUserWithValidCredentials() {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setUsername(USER_PHONE);
        createUserDto.setPassword(USER_PASSWORD);
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters.fromObject(createUserDto))
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.post()
                .uri("/login")
                .body(BodyInserters.fromFormData("username", USER_PHONE).with("password", USER_PASSWORD))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void shouldReturnUnauthorizedWhenAnonymousUserTriesToUpdateUser() {
        webTestClient.put()
                .uri("/users/1")
                .body(BodyInserters.fromObject(new UpdateUserDto()))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void shouldReturnForbiddenWhenUserTriesToUpdateAnotherUser() {
        createUser();
        webTestClient.put()
                .uri("/users/1")
                .header(AUTHORIZATION_HEADER, basic())
                .body(BodyInserters.fromObject(new UpdateUserDto()))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void shouldUpdateUserWhenUserUpdatesHisProfile() {
        createUser();
        UserDetails userDetails = customUserDetailsRepository.findByUsername(USERNAME).block();
        EntityExchangeResult<User> result = webTestClient.put()
                .uri("/users/" + ((CustomUserDetails) userDetails).getId())
                .header(AUTHORIZATION_HEADER, basic())
                .body(BodyInserters.fromObject(updateUserDto()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult();

        User user = result.getResponseBody();
        assertEquals(NEW_FAMILY_NAME, user.getFamilyName());
        assertEquals(NEW_GIVEN_NAME, user.getGivenName());
        assertEquals(NEW_BIRTH_DATE, user.getBirthDate());
        assertEquals(NEW_CITY, user.getCity());
    }

    private UpdateUserDto updateUserDto() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFamilyName(NEW_FAMILY_NAME);
        updateUserDto.setGivenName(NEW_GIVEN_NAME);
        updateUserDto.setBirthDate(NEW_BIRTH_DATE);
        updateUserDto.setCity(NEW_CITY);
        return updateUserDto;
    }
}