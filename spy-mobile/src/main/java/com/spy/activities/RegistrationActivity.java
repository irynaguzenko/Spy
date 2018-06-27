package com.spy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spy.dagger.SpyApplication;
import com.spy.dto.CreateUserDto;
import com.spy.services.SaveSharedPreferences;
import com.spy.services.SpyService;
import com.spyapp.R;

import javax.inject.Inject;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AbstractActivity {

    @Inject
    SpyService spyService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ((SpyApplication) getApplication()).getApiComponent().inject(this);
        initLoginTextView();


        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(l -> register());
    }

    private void register() {
        String phone = ((EditText) findViewById(R.id.phoneEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        spyService.register(new CreateUserDto(phone, password)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Registration", "Successful");
                    SaveSharedPreferences.setUserName(RegistrationActivity.this, Credentials.basic(phone, password));
                    goTo(EditProfileActivity.class);
                } else {
                    onFailure(call, new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("Registration", "Failure");
//                TODO: handle errors
            }
        });
    }

    private void initLoginTextView() {
        TextView login = findViewById(R.id.loginTextView);
        login.setOnClickListener(l -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
