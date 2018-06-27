package com.spy.dagger;

import android.app.Application;

import com.spy.dagger.component.ApiComponent;
import com.spy.dagger.module.ApiModule;
import com.spy.dagger.component.DaggerApiComponent;

public class SpyApplication extends Application {
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        apiComponent = DaggerApiComponent
                .builder()
//                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(this.getApplicationContext()))
                .build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
