package com.example.daggerproject.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";
    private final SessionManager mSessionManager;
    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        Log.d(TAG, "ProfileViewModel: viewModel is ready... ");
        this.mSessionManager = sessionManager;
    }

    public LiveData<AuthResource<UserBody>> getAuthenticatedUser() {
        return mSessionManager.getAuthUser();
    }
}
