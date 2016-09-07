package com.hasbrain.areyouandroiddev.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.NetworkBasedFeedDatastore;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

public class PostListFromInternetActivity extends BasePostListActivity {

    ListView lsvPostList;
    GridView grvPostList;
    PostListAdapter adaPostList;
    int mScreenMode;
    private FeedDataStore feedDataStore;
    LinearLayout linearLayout;
    Button btnRetry;

    @Override
    protected void loadRedditPost() {
        if (hasConnection()) {
            feedDataStore = new NetworkBasedFeedDatastore(DATA_JSON_HTTP);
            feedDataStore.getPostList(new FeedDataStore.OnRedditPostsRetrievedListener() {
                @Override
                public void onRedditPostsRetrieved(List<RedditPost> postList, Exception ex) {
                    displayPostList(postList);
                }
            });
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            lsvPostList.setVisibility(View.GONE);
        }
    }

    protected void initView() {
        super.initView();
        //Layout DisConnect
        linearLayout = (LinearLayout)findViewById(R.id.layout_disconnect);
        linearLayout.setVisibility(View.GONE);
        btnRetry  =(Button)findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                lsvPostList.setVisibility(View.VISIBLE);
                loadRedditPost();
            }
        });

        mScreenMode = getResources().getConfiguration().orientation;
        adaPostList = new PostListAdapter(this, R.layout.post_list_item, this, mScreenMode);
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            lsvPostList = (ListView) findViewById(R.id.lsvPostList);
            lsvPostList.setAdapter(adaPostList);
            lsvPostList.setVisibility(View.VISIBLE);
        }

    }

    protected void displayPostList(List<RedditPost> postList) {
        //TODO: Display post list.
        adaPostList.setPostList(postList);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_list_from_internet;
    }

    @Override
    public void onRefresh() {
        adaPostList.setPostList(null);
        loadRedditPost();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    public  boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }
}
