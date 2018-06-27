package com.spy.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.spyapp.R;

public class EditProfileActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
    }
}
