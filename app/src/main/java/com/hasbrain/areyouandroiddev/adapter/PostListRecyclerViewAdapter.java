package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by Levin on 17/04/2016.
 */
public class PostListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener {
    private static final int ITEM = 0, FOOTER = 1;
    private Context mContext;
    private int mLayoutID;
    private List<RedditPost> mListPost;
    private int mScreenMode;
    private OnItemClick callBack;

    public PostListRecyclerViewAdapter(Context mContext, int mLayoutID, List<RedditPost> mListPost, int mScreenMode) {
        this.mContext = mContext;
        this.mLayoutID = mLayoutID;
        this.mListPost = mListPost;
        this.mScreenMode = mScreenMode;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM:
                view = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
                break;
            case FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.footer_list_view, parent, false);
                view.setOnClickListener(this);
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
                break;
        }
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        RedditPost obj = getObjectAtPosition(position);
        if (obj != null) {
            holder.bindData(obj,mScreenMode);
            holder.bindOnClick(position, callBack);
        }
    }

    public RedditPost getObjectAtPosition(int position) {
        if (mListPost == null || position > mListPost.size() - 1)
            return null;
        else return mListPost.get(position);
    }

    @Override
    public int getItemCount() {
        if (mListPost == null)
            return 0;
        return mListPost.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (getItemCount() - 1))
            return FOOTER;
        return ITEM;
    }

    @Override
    public void onClick(View v) {
        callBack.onItemClick(getItemCount() - 1);
    }

    public void setOnItemClick(OnItemClick c) {
        callBack = c;
    }
}
