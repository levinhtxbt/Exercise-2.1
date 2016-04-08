package com.hasbrain.areyouandroiddev;

import android.content.Intent;
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
    ExpandableListView expandableListView;
    PostInSectionAdapter adapterPostInSection;
    List<String> mListSection;
    HashMap<String, List<RedditPost>> mListChild;
    int mScreenMode;

    @Override
    protected void displayPostList(final List<RedditPost> postList) {
        //TODO: Display posts in sections.
        // Bind to List
        mListSection = new ArrayList<>();
        mListSection.add("Sticky posts");
        mListSection.add("Normal posts");
        List<RedditPost> mStickyPosts = new ArrayList<>();
        List<RedditPost> mNormalPosts = new ArrayList<>();
        for (RedditPost p : postList) {
            if (p.isStickyPost())
                mStickyPosts.add(p);
            else mNormalPosts.add(p);
        }
        mListChild = new HashMap<String, List<RedditPost>>();
        mListChild.put(mListSection.get(0), mStickyPosts);
        mListChild.put(mListSection.get(1), mNormalPosts);

        expandableListView = (ExpandableListView) findViewById(R.id.expandListViewPostList);
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            mScreenMode = PostListActivity.ORIENTATION_PORTRAIT;
        } else {
            mScreenMode = PostListActivity.ORIENTATION_LANDSCAPE;
        }
        adapterPostInSection = new PostInSectionAdapter(this.getBaseContext(), R.layout.group_post_in_sector, R.layout.post_list_item, mListSection, mListChild, mScreenMode);
        expandableListView.setAdapter(adapterPostInSection);
        TextView footer_listView = (TextView) getLayoutInflater().inflate(R.layout.footer_list_view, null).findViewById(R.id.lblFooter);
        expandableListView.addFooterView(footer_listView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent i = new Intent(PostInSectionActivity.this, PostViewActivity.class);
                RedditPost post = mListChild.get(mListSection.get(groupPosition)).get(childPosition);
                i.putExtra("URL", post.getUrl());
                startActivity(i);
                return true;
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }
}
