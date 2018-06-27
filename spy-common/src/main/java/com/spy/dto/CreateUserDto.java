package com.spy.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreateUserDto {
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$")
    private String password;

    public CreateUserDto() {
    }

    public CreateUserDto(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
