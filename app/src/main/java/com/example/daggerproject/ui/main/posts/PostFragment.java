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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerproject.R;
import com.example.daggerproject.models.post.PostBody;
import com.example.daggerproject.ui.main.Resource;
import com.example.daggerproject.utils.VerticalSpacingItemDecoration;
import com.example.daggerproject.viewModel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {
    private static final String TAG = "PostFragment";
    @Inject
    ViewModelProviderFactory mProviderFactory;
    @Inject
    PostsRecyclerAdapter mAdapter;

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

        initRecyclerView();
        subscribeObservers();

    }

    private void subscribeObservers() {
        mViewModel.observePosts().removeObservers(getViewLifecycleOwner());
        mViewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<PostBody>>>() {
            @Override
            public void onChanged(Resource<List<PostBody>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case SUCCESS:
                            Log.d(TAG, "onChanged: getPosts...");
                            mAdapter.setPosts(listResource.data);
                            break;

                        case LOADING:
                            Log.d(TAG, "onChanged: Loading...");
                            break;
                            
                        case ERROR:
                            Log.d(TAG, "onChanged: ERROR..." + listResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }
}
