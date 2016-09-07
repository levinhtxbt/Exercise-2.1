package com.hasbrain.areyouandroiddev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by levinhtxbt@gmail.com on 30/08/2016.
 */
public class BasePostListActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener,OnItemClick {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    public static final String DATA_JSON_HTTP = "https://www.reddit.com/r/androiddev/new.json";
    public static final int ORIENTATION_PORTRAIT = 1;
    public static final int ORIENTATION_LANDSCAPE = 2;
    SwipeRefreshLayout swipeRefreshLayout;
    private FeedDataStore feedDataStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initView();
        loadRedditPost();

    }

    protected void loadRedditPost(){
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

    protected void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }

    protected void displayPostList(List<RedditPost> postList) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(String url) {
        Intent i = new Intent(this, PostViewActivity.class);
        i.putExtra("URL", url);
        startActivity(i);
    }
}
