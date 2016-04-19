package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.adapter.PostInSectionAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends PostListActivity {
    private static final String STICKY_POST = "Sticky posts";
    private static final String NORMAL_POST = "Normal posts";
    ExpandableListView expandableListView;
    PostInSectionAdapter adapterPostInSection;
    List<String> mListSection;
    HashMap<String, List<RedditPost>> mListChild;


    @Override
    protected void displayPostList(final List<RedditPost> postList) {
        //TODO: Display posts in sections.
        // Bind to List
        mListPost = postList;
        initHashMap();
        expandableListView = (ExpandableListView) findViewById(R.id.expandListViewPostList);
        adapterPostInSection = new PostInSectionAdapter(this.getBaseContext(), R.layout.group_post_in_sector, R.layout.post_list_item, mListSection, mListChild, mScreenMode);
        expandableListView.setAdapter(adapterPostInSection);
        TextView footer_listView = (TextView) getLayoutInflater().inflate(R.layout.footer_list_view, null).findViewById(R.id.lblFooter);
        expandableListView.addFooterView(footer_listView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                RedditPost post = mListChild.get(mListSection.get(groupPosition)).get(childPosition);
                startPostView(post.getUrl());
                return true;
            }
        });

    }

    public void initHashMap() {
        mListSection = new ArrayList<>();
        mListSection.add(STICKY_POST);
        mListSection.add(NORMAL_POST);
        List<RedditPost> mStickyPosts = new ArrayList<>();
        List<RedditPost> mNormalPosts = new ArrayList<>();
        for (RedditPost p : mListPost) {
            if (p.isStickyPost())
                mStickyPosts.add(p);
            else mNormalPosts.add(p);
        }
        mListChild = new HashMap<String, List<RedditPost>>();
        mListChild.put(mListSection.get(0), mStickyPosts);
        mListChild.put(mListSection.get(1), mNormalPosts);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
