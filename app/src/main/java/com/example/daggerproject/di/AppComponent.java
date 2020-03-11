package com.example.daggerproject.di;

import android.app.Application;

import com.example.daggerproject.ActivityBuildersModule;
import com.example.daggerproject.AppModule;
import com.example.daggerproject.BaseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        /*ActivityBuildersModule.class,
        AppModule.class*/})
public interface AppComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
