package com.hasbrain.areyouandroiddev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hasbrain.areyouandroiddev.adapter.ExpandableRecyclerViewAdapter;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

public class PostInSectionRecyclerViewActivity extends PostInSectionActivity {

    RecyclerView expandRecyclerView;
    ExpandableRecyclerViewAdapter adapter;
    @Override
    protected void displayPostList(List<RedditPost> postList) {
        mListPost = postList;
        initHashMap();

        expandRecyclerView = (RecyclerView)findViewById(R.id.expandRecyclerView);
        expandRecyclerView.setHasFixedSize(true);
        expandRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpandableRecyclerViewAdapter(this,mListSection,mListChild,mScreenMode);
        expandRecyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if(position == ExpandableRecyclerViewAdapter.TYPE_FOOTER)
                    startPostView(URL);
            }
            @Override
            public void onItemClick(RedditPost post) {
                startPostView(post.getUrl());
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section_recycler_view;
    }
}
