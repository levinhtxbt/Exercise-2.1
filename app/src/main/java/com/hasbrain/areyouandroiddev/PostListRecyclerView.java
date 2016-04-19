package com.hasbrain.areyouandroiddev;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.hasbrain.areyouandroiddev.adapter.PostListRecyclerViewAdapter;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

public class PostListRecyclerView extends PostListActivity {

    RecyclerView recyclerViewPostList;
    PostListRecyclerViewAdapter adapter;

    @Override
    protected void displayPostList(final List<RedditPost> postList) {
        recyclerViewPostList = (RecyclerView)findViewById(R.id.recyclerViewPostList);
        recyclerViewPostList.setHasFixedSize(true);

        if (mScreenMode == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerViewPostList.setLayoutManager(new GridLayoutManager(this, 3));
        }
        adapter = new PostListRecyclerViewAdapter(this,R.layout.post_list_item,postList,mScreenMode);
        recyclerViewPostList.setAdapter(adapter);
        adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if(position == adapter.getItemCount()-1)
                    startPostView(URL);
                else {
                    RedditPost post = postList.get(position);
                    startPostView(post.getUrl());
                }
            }

            @Override
            public void onItemClick(RedditPost post) {

            }

        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_list_recycler_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
