package com.example.daggerproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

public class AuthActivity extends AppCompatActivity {
    @Inject
    Drawable logo;
    @Inject
    RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setLogo();
    }

    private void setLogo() {
        mRequestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }
}
