package com.example.daggerproject.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.R;
import com.example.daggerproject.viewModel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {
    private AuthViewModel mAuthViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable logo;
    @Inject
    RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setLogo();
        mAuthViewModel = new ViewModelProvider(this , providerFactory).get(AuthViewModel.class);
    }

    private void setLogo() {
        mRequestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }
}
