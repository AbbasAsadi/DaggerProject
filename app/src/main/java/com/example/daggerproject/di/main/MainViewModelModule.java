package com.example.daggerproject.di.main;

import androidx.lifecycle.ViewModel;

import com.example.daggerproject.di.ViewModelKey;
import com.example.daggerproject.ui.main.posts.PostViewModel;
import com.example.daggerproject.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel postViewModel);

}
