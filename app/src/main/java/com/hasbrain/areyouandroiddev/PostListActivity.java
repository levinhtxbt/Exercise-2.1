package com.hasbrain.areyouandroiddev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    public static final int ORIENTATION_PORTRAIT = 1;
    public static final int ORIENTATION_LANDSCAPE = 2;
    private FeedDataStore feedDataStore;
    ListView lsvPostList;
    GridView grvPostList;
    PostListAdapter adaPostList;
    List<RedditPost> mListPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
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
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            lsvPostList = (ListView) findViewById(R.id.lsvPostList);
            adaPostList = new PostListAdapter(this, R.layout.post_list_item, postList, ORIENTATION_PORTRAIT);
            lsvPostList.setAdapter(adaPostList);
            lsvPostList.addFooterView(footer_listView);
            lsvPostList.setOnItemClickListener(this);

        } else {
            grvPostList = (GridView) findViewById(R.id.grvPostList);
            adaPostList = new PostListAdapter(this, R.layout.post_list_item, postList, ORIENTATION_LANDSCAPE);
            grvPostList.setAdapter(adaPostList);
            grvPostList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, PostViewActivity.class);
        if (position < mListPost.size()) {

            i.putExtra("URL", mListPost.get(position).getUrl());

        } else {
            i.putExtra("URL", "https://www.reddit.com/r/androiddev/");
        }
        startActivity(i);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }
}
