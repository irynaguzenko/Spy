package com.spy.dagger.module;

import android.content.Context;

import com.spy.services.SaveSharedPreferences;
import com.spy.services.SpyService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule {

    private Context context;

    public ApiModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SpyService spyService() {
        return retrofit().create(SpyService.class);
    }

    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(SpyService.SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request authorization = chain.request().newBuilder()
                            .addHeader("Authorization", SaveSharedPreferences.getUserName(context))
                            .build();
                    return chain.proceed(authorization);
                })
                .build();
    }
}
