package com.hasbrain.areyouandroiddev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PostListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    public static  final String URL = "https://www.reddit.com/r/androiddev/";
    private FeedDataStore feedDataStore;
    ListView lsvPostList;
    GridView grvPostList;
    PostListAdapter adaPostList;
    List<RedditPost> mListPost;
    int mScreenMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mScreenMode = getResources().getConfiguration().orientation;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
        Gson gson = gsonBuilder.create();
        InputStream is = null;
        try {
            is = getAssets().open(DATA_JSON_FILE_NAME);
            feedDataStore = new FileBasedFeedDataStore(gson, is);
            feedDataStore.getPostList(new FeedDataStore.OnRedditPostsRetrievedListener() {
                @Override
                public void onRedditPostsRetrieved(List<RedditPost> postList, Exception ex) {
                    displayPostList(postList);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void displayPostList(final List<RedditPost> postList) {
        //TODO: Display post list.
        mListPost = postList;
        TextView footer_listView = (TextView) getLayoutInflater().inflate(R.layout.footer_list_view, null).findViewById(R.id.lblFooter);
        if (mScreenMode == Configuration.ORIENTATION_PORTRAIT) {
            lsvPostList = (ListView) findViewById(R.id.lsvPostList);
            adaPostList = new PostListAdapter(this, R.layout.post_list_item, postList, mScreenMode);
            lsvPostList.setAdapter(adaPostList);
            lsvPostList.addFooterView(footer_listView);
            lsvPostList.setOnItemClickListener(this);
        } else {
            grvPostList = (GridView) findViewById(R.id.grvPostList);
            adaPostList = new PostListAdapter(this, R.layout.post_list_item, postList,mScreenMode);
            grvPostList.setAdapter(adaPostList);
            grvPostList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position < mListPost.size()) {
            startPostView(mListPost.get(position).getUrl());
        } else {
            startPostView(URL);
        }
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.mnuPostInSection:
                i = new Intent(PostListActivity.this,PostInSectionActivity.class);
                startActivity(i);
                break;
            case R.id.mnuPostListRecyclerView:
                i = new Intent(PostListActivity.this,PostListRecyclerView.class);
                startActivity(i);
                break;
            case R.id.mnuPostInSectiontRecyclerView:
                i = new Intent(PostListActivity.this,PostInSectionRecyclerViewActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startPostView(String url){
        Intent i = new Intent(this, PostViewActivity.class);
        i.putExtra("URL", url);
        startActivity(i);
    }
}
