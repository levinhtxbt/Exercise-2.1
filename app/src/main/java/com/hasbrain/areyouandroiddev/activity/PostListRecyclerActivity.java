package com.hasbrain.areyouandroiddev.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.PostListRecyclerAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

public class PostListRecyclerActivity extends BasePostListActivity {

    RecyclerView mRecyclerView;
    PostListRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_list_recycler;
    }

    @Override
    protected void initView() {
        int orientation = getResources().getConfiguration().orientation;
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvRedditPost);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(orientation == BasePostListActivity.ORIENTATION_LANDSCAPE ?
                new GridLayoutManager(this, 3) : new LinearLayoutManager(this));
        mAdapter = new PostListRecyclerAdapter(this, this, orientation);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void displayPostList(List<RedditPost> postList) {
        super.displayPostList(postList);
        mAdapter.setRedditPost(postList);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        final List<RedditPost> list = mAdapter.getListRedditPost();
        mAdapter.setRedditPost(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setRedditPost(list);
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}
