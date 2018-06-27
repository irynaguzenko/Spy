package com.spy.services;

import com.spy.dto.CreateUserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SpyService {
    String SERVER_URL = "http://10.0.2.2:8080";

    @FormUrlEncoded
    @POST("/login")
    Call<Void> login(@Field("username") String username, @Field("password") String password);

    @POST("users")
    Call<Void> register(@Body CreateUserDto userDto);
}
