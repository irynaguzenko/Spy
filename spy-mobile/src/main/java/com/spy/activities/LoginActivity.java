package com.spy.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spy.dagger.SpyApplication;
import com.spy.services.SaveSharedPreferences;
import com.spy.services.SpyService;
import com.spyapp.R;

import javax.inject.Inject;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AbstractActivity {

    @Inject
    SpyService spyService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ((SpyApplication) getApplication()).getApiComponent().inject(this);
        initSignUpTextView();

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(l -> login());
    }

    private void initSignUpTextView() {
        TextView signUp = findViewById(R.id.signUpTextView);
        signUp.setOnClickListener(l -> goTo(RegistrationActivity.class));
    }

    private void login() {
        String phone = ((EditText) findViewById(R.id.phoneEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        spyService.login(phone, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Login", "Success");
                    SaveSharedPreferences.setUserName(LoginActivity.this, Credentials.basic(phone, password));
                    goTo(EventsActivity.class);
                } else {
                    onFailure(call, new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("Login", "Failure");
//                TODO: handle errors
            }
        });
    }
}
