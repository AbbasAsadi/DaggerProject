package com.example.daggerproject.network.main;

import com.example.daggerproject.models.post.PostBody;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {
    //posts?userId=1request
    @GET("posts")
    Flowable<List<PostBody>> getPostFromUser(
            @Query("userId") int userId
    );
}
