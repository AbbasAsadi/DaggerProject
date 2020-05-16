package com.example.daggerproject.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private final AuthApi mAuthApi;
    private SessionManager mSessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.mAuthApi = authApi;
        this.mSessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel:view model is working... ");

    }

    public void authenticationWithId(final int userId) {
        Log.d(TAG, "authenticationWithId: attempting to login ");
        mSessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<UserBody>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
                mAuthApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, UserBody>() {
                            @Override
                            public UserBody apply(Throwable throwable) throws Exception {
                                UserBody errorBody = new UserBody();
                                errorBody.setId(-1);
                                return errorBody;
                            }
                        })
                        .map(new Function<UserBody, AuthResource<UserBody>>() {
                            @Override
                            public AuthResource<UserBody> apply(UserBody responseBody) throws Exception {
                                if (responseBody.getId() == -1) {
                                    return AuthResource.error("Could not authenticate", null);
                                }
                                return AuthResource.authenticated(responseBody);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<UserBody>> observeAuthState() {
        return mSessionManager.getAuthUser();
    }
}
