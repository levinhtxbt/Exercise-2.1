package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.PostListActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by Levin on 03/04/2016.
 */
public class PostListAdapter extends ArrayAdapter<RedditPost> {

    Context mContext;
    int mLayoutID;
    List<RedditPost> mListRedditPost;
    int mScreenMode;

    public PostListAdapter(Context context, int resource, List<RedditPost> objects, int screen_mode) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayoutID = resource;
        this.mListRedditPost = objects;
        this.mScreenMode = screen_mode;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostListViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
            viewHolder = new PostListViewHolder();
            viewHolder.lblFirst = (TextView) convertView.findViewById(R.id.lblFirst);
            viewHolder.lblSecond = (TextView) convertView.findViewById(R.id.lblSecond);
            viewHolder.lblThird = (TextView) convertView.findViewById(R.id.lblThird);
            viewHolder.lblScore = (TextView) convertView.findViewById(R.id.lblScore);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PostListViewHolder) convertView.getTag();
        }

        //Bind data
        RedditPost obj = mListRedditPost.get(position);
        viewHolder.bindData(obj,mScreenMode);
        return convertView;
    }
}

