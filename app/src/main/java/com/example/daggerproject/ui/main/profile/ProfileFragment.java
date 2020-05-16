package com.example.daggerproject.ui.main.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daggerproject.R;
import com.example.daggerproject.models.user.UserBody;
import com.example.daggerproject.ui.auth.AuthResource;
import com.example.daggerproject.viewModel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel mViewModel;
    private TextView mTextViewEmail;
    private TextView mTextViewUserName;
    private TextView mTextViewWebsite;

    @Inject
    ViewModelProviderFactory mProviderFactory;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: profileFragment was created...");
        mViewModel = new ViewModelProvider(this , mProviderFactory).get(ProfileViewModel.class);
        mTextViewEmail = view.findViewById(R.id.email);
        mTextViewUserName = view.findViewById(R.id.username);
        mTextViewWebsite = view.findViewById(R.id.website);
        subscribeObservers();
    }
    private void subscribeObservers() {
        mViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        mViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<UserBody>>() {
            @Override
            public void onChanged(AuthResource<UserBody> responseBodyAuthResource) {
                if (responseBodyAuthResource != null) {
                    switch (responseBodyAuthResource.status){
                        case AUTHENTICATED:
                            setUserDetails(responseBodyAuthResource.data);
                            break;
                        case NOT_AUTHENTICATED:

                            break;
                        case LOADING:

                            break;
                        case ERROR:
                            setErrorDetails(responseBodyAuthResource.message);
                            break;
                    }
                }

            }
        });
    }

    private void setErrorDetails(String message) {
        mTextViewEmail.setText(message);
        mTextViewUserName.setText("error");
        mTextViewWebsite.setText("error");
    }

    private void setUserDetails(UserBody data) {
        mTextViewEmail.setText(data.getEmail());
        mTextViewUserName.setText(data.getUsername());
        mTextViewWebsite.setText(data.getWebsite());
    }
}
