package com.spy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spyapp.R;
import com.spy.services.SaveSharedPreferences;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        SaveSharedPreferences.clearUserName(this);
        if (SaveSharedPreferences.getUserName(this).isEmpty()) {
            goToLoginPage();
        }
    }

    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
