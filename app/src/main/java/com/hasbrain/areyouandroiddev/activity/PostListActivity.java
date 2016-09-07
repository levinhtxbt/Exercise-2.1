package com.hasbrain.areyouandroiddev.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;


public class PostListActivity extends BasePostListActivity {

    ListView lsvPostList;
    GridView grvPostList;
    PostListAdapter adaPostList;
    private int orientation;

    @Override
    protected void initView() {
        super.initView();
//        TextView footer_listView = (TextView) getLayoutInflater()
//                .inflate(R.layout.footer_list_view, null)
//                .findViewById(R.id.lblFooter);
        orientation = getResources().getConfiguration().orientation;
        adaPostList = new PostListAdapter(this, R.layout.post_list_item,this,orientation);
        if (orientation == ORIENTATION_PORTRAIT) {
            lsvPostList = (ListView) findViewById(R.id.lsvPostList);
            lsvPostList.setAdapter(adaPostList);
//            lsvPostList.addFooterView(footer_listView);
        } else {
            grvPostList = (GridView) findViewById(R.id.grvPostList);
            grvPostList.setAdapter(adaPostList);
        }
    }

    @Override
    protected void displayPostList(List<RedditPost> postList) {
        super.displayPostList(postList);
        adaPostList.setPostList(postList);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.mnuHttpConnection:
                i = new Intent(PostListActivity.this, PostListFromInternetActivity.class);
                startActivity(i);
                break;
            case R.id.mnuPostInSection:
                i = new Intent(PostListActivity.this, PostInSectionActivity.class);
                startActivity(i);
                break;
            case R.id.mnuPostListRecyclerView:
                i = new Intent(PostListActivity.this, PostListRecyclerActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        final List<RedditPost> list = adaPostList.getPostList();
        adaPostList.setPostList(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adaPostList.setPostList(list);
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
}
