package com.example.daggerproject.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.models.post.PostBody;
import com.example.daggerproject.network.main.MainApi;
import com.example.daggerproject.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {

    private static final String TAG = "PostsViewModel";

    // inject
    private final SessionManager sessionManager;
    private final MainApi mainApi;

    private MediatorLiveData<Resource<List<PostBody>>> posts;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostsViewModel: viewmodel is working...");
    }

    public LiveData<Resource<List<PostBody>>> observePosts(){
        if(posts == null){
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<PostBody>)null));

            final LiveData<Resource<List<PostBody>>> source = LiveDataReactiveStreams.fromPublisher(

                    mainApi.getPostFromUser(sessionManager.getAuthUser().getValue().data.getId())

                            .onErrorReturn(new Function<Throwable, List<PostBody>>() {
                                @Override
                                public List<PostBody> apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: ", throwable);
                                    PostBody post = new PostBody();
                                    post.setId(-1);
                                    ArrayList<PostBody> posts = new ArrayList<>();
                                    posts.add(post);
                                    return posts;
                                }
                            })

                            .map(new Function<List<PostBody>, Resource<List<PostBody>>>() {
                                @Override
                                public Resource<List<PostBody>> apply(List<PostBody> posts) throws Exception {

                                    if(posts.size() > 0){
                                        if(posts.get(0).getId() == -1){
                                            return Resource.error("Something went wrong", null);
                                        }
                                    }

                                    return Resource.success(posts);
                                }
                            })

                            .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, new Observer<Resource<List<PostBody>>>() {
                @Override
                public void onChanged(Resource<List<PostBody>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }

}

