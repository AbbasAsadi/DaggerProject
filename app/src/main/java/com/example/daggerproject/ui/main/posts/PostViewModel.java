package com.example.daggerproject.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.network.main.MainApi;

import javax.inject.Inject;

public class PostViewModel extends ViewModel {
    private static final String TAG = "PostViewModel";
    private final SessionManager mSessionManager;
    private final MainApi mMainApi;

    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi) {
        mSessionManager = sessionManager;
        mMainApi = mainApi;
        Log.d(TAG, "PostViewModel: viewModel is working....");
    }
}
