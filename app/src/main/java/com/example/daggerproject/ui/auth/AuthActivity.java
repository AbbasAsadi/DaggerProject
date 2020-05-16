package com.example.daggerproject.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.R;
import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.ui.main.MainActivity;
import com.example.daggerproject.viewModel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable logo;
    @Inject
    RequestManager mRequestManager;
    private AuthViewModel mAuthViewModel;

    private EditText mEditTextUserId;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mEditTextUserId = findViewById(R.id.user_id_input);
        mProgressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);
        mAuthViewModel = new ViewModelProvider(this, providerFactory).get(AuthViewModel.class);
        subscribeObserves();
        setLogo();
    }

    private void subscribeObserves() {
        mAuthViewModel.observeAuthState().observe(this, new Observer<AuthResource<UserBody>>() {
            @Override
            public void onChanged(AuthResource<UserBody> responseBodyAuthResource) {
                if (responseBodyAuthResource != null) {
                    switch (responseBodyAuthResource.status) {
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,
                                    "\n did you enter an number between 1 and 10 ",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        case AUTHENTICATED:
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LogIn Success "
                                    +responseBodyAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        }else
            mProgressBar.setVisibility(View.GONE);
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {
        mRequestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(mEditTextUserId.getText().toString())) {
            Log.d(TAG, "attemptLogin: empty");
            return;
        }
        mAuthViewModel.authenticationWithId(Integer.parseInt(mEditTextUserId.getText().toString()));
        Log.d(TAG, "attemptLogin: not empty");
    }
}
