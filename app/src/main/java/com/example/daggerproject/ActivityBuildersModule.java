package com.example.daggerproject;

import com.example.daggerproject.AuthActivity;

import dagger.Component;
import dagger.android.ContributesAndroidInjector;

@Component
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();

}
