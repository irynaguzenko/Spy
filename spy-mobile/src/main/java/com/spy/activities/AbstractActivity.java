package com.spy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {

    protected void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
