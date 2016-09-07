package com.hasbrain.areyouandroiddev.activity;

import android.widget.ExpandableListView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.PostInSectionAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends BasePostListActivity {

    ExpandableListView expandableListView;
    PostInSectionAdapter adapterPostInSection;
    List<String> mListSection;
    int mScreenMode;

    @Override
    protected void initView() {
        super.initView();
        mListSection = new ArrayList<>();
        mListSection.add("Sticky posts");
        mListSection.add("Normal posts");
        mListSection.add("Bottom");

        expandableListView = (ExpandableListView) findViewById(R.id.expandListViewPostList);
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            mScreenMode = ORIENTATION_PORTRAIT;
        } else {
            mScreenMode = ORIENTATION_LANDSCAPE;
        }
        adapterPostInSection = new PostInSectionAdapter(this, R.layout.group_post_in_sector,
                R.layout.post_list_item, mListSection, null,this, mScreenMode);
        expandableListView.setAdapter(adapterPostInSection);
    }

    @Override
    protected void displayPostList(final List<RedditPost> postList) {
        //TODO: Display posts in sections.
        // Bind to List
        HashMap<String,List<RedditPost>> listChild = adapterPostInSection.getChildList();
        List<RedditPost> mStickyPosts = new ArrayList<>();
        List<RedditPost> mNormalPosts = new ArrayList<>();
        for (RedditPost p : postList) {
            if (p.isStickyPost())
                mStickyPosts.add(p);
            else mNormalPosts.add(p);
        }
        listChild.put(mListSection.get(0), mStickyPosts);
        listChild.put(mListSection.get(1), mNormalPosts);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }

}
