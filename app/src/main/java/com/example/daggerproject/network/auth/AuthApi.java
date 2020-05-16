package com.example.daggerproject.network.auth;

import com.example.daggerproject.models.user.UserBody;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("users/{id}")
    Flowable<UserBody> getUser(@Path("id") int id);
}
