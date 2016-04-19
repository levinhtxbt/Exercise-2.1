package com.hasbrain.areyouandroiddev.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import org.w3c.dom.Text;

/**
 * Created by Levin on 03/04/2016.
 */
public class PostListViewHolder {
    TextView lblFirst;
    TextView lblSecond;
    TextView lblThird;
    TextView lblScore;

    public void bindData(RedditPost obj,int mScreenMode){
        lblScore.setText(String.valueOf(obj.getScore()));
        lblScore.setTextColor(Color.GRAY);
        lblFirst.setText(Utils.buildTextViewFirst(obj.getAuthor(), obj.getSubreddit(), mScreenMode));
        lblSecond.setText(Utils.buildTextViewSecond(obj.getTitle(), obj.isStickyPost(), mScreenMode));
        lblThird.setText(Utils.buildTextViewThird(obj.getCommentCount(), obj.getDomain(), obj.getCreatedUTC()));
    }
}
