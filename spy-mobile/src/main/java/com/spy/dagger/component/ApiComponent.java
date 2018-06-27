package com.spy.dagger.component;

import com.spy.dagger.module.ApiModule;
import com.spy.dagger.module.ApplicationModule;
import com.spy.activities.LoginActivity;
import com.spy.activities.RegistrationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApiComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegistrationActivity registrationActivity);
}
