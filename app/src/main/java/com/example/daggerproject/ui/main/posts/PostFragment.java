package com.example.daggerproject.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerproject.R;
import com.example.daggerproject.models.post.PostBody;
import com.example.daggerproject.ui.main.Resource;
import com.example.daggerproject.viewModel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {
    private static final String TAG = "PostFragment";
    @Inject
    ViewModelProviderFactory mProviderFactory;
    private PostsViewModel mViewModel;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mViewModel = new ViewModelProvider(this, mProviderFactory).get(PostsViewModel.class);

    }
    private void subscribeObservers() {
        mViewModel.observePosts().removeObservers(getViewLifecycleOwner());
        mViewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<PostBody>>>() {
            @Override
            public void onChanged(Resource<List<PostBody>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: " + listResource.data);
                }
            }
        });
    }
}
