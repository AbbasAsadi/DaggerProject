package com.example.daggerproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";
    private MediatorLiveData<AuthResource<UserBody>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {

    }
    public void authenticateWithId (final LiveData<AuthResource<UserBody>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading((UserBody)null));
            cachedUser.addSource(source, new Observer<AuthResource<UserBody>>() {
                @Override
                public void onChanged(AuthResource<UserBody> responseBodyAuthResource) {
                    cachedUser.setValue(responseBodyAuthResource);
                    cachedUser.removeSource(source);

                }
            });
        }else {
            Log.d(TAG, "authenticateWithId: previous session detected. Retrieving user from cache.");
        }
    }
    
    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<UserBody>logout());
    }

    public LiveData<AuthResource<UserBody>> getAuthUser() {
        return cachedUser;
    }
    
}
