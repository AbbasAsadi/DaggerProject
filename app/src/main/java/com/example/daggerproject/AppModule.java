package com.example.daggerproject;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.daggerproject.R;

import dagger.Component;
import dagger.Provides;

@Component
public class AppModule {
    @Provides
    static RequestOptions provideRequestOption() {
        return RequestOptions
                .placeholderOf(R.drawable.logo)
                .error(R.drawable.white_background);
    }
    @Provides
    static RequestManager provideGlideInstance(Application application,
                                               RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application , R.drawable.logo);
    }
}
