package com.example.daggerproject;

import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.ui.auth.AuthActivity;
import com.example.daggerproject.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager mSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        mSessionManager.getAuthUser().observe(this, new Observer<AuthResource<UserBody>>() {
            @Override
            public void onChanged(AuthResource<UserBody> responseBodyAuthResource) {
                if (responseBodyAuthResource != null) {
                    switch (responseBodyAuthResource.status) {
                        case ERROR:
                            Toast.makeText(BaseActivity.this,
                                    "\n did you enter an number between 1 and 10 ",
                                    Toast.LENGTH_SHORT)
                                    .show();
                             break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: LogIn Success "
                                    +responseBodyAuthResource.data.getEmail());
                            break;
                        case LOADING:
                            break;
                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this , AuthActivity.class);
        startActivity(intent);
        finish();
    }
}

