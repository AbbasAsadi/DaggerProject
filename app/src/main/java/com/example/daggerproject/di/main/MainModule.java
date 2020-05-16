package com.example.daggerproject.di.main;

import com.example.daggerproject.network.main.MainApi;
import com.example.daggerproject.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostsRecyclerAdapter sPostsRecyclerAdapter() {
        return new PostsRecyclerAdapter();
    }
}
