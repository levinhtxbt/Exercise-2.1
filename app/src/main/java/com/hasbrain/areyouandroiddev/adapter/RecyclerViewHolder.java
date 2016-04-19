package com.hasbrain.areyouandroiddev.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

/**
 * Created by Levin on 19/04/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView lblFirst;
    TextView lblSecond;
    TextView lblThird;
    TextView lblScore;

    TextView lblHeader;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        lblFirst = (TextView) itemView.findViewById(R.id.lblFirst);
        lblSecond = (TextView) itemView.findViewById(R.id.lblSecond);
        lblThird = (TextView) itemView.findViewById(R.id.lblThird);
        lblScore = (TextView) itemView.findViewById(R.id.lblScore);
    }

    public RecyclerViewHolder(View itemView, boolean isHeader) {
        super(itemView);
        if (isHeader) {
            lblHeader = (TextView)itemView.findViewById(R.id.lblTitleGroupPostInSection);
        } else {
            lblFirst = (TextView) itemView.findViewById(R.id.lblFirst);
            lblSecond = (TextView) itemView.findViewById(R.id.lblSecond);
            lblThird = (TextView) itemView.findViewById(R.id.lblThird);
            lblScore = (TextView) itemView.findViewById(R.id.lblScore);
        }
    }

    public  void bindData(RedditPost obj,int mScreenMode){
        lblScore.setText(String.valueOf(obj.getScore()));
        lblScore.setTextColor(Color.GRAY);
        lblFirst.setText(Utils.buildTextViewFirst(obj.getAuthor(), obj.getSubreddit(), mScreenMode));
        lblSecond.setText(Utils.buildTextViewSecond(obj.getTitle(), obj.isStickyPost(), mScreenMode));
        lblThird.setText(Utils.buildTextViewThird(obj.getCommentCount(), obj.getDomain(), obj.getCreatedUTC()));
    }

    public void bindOnClick(final int position, final OnItemClick callback) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onItemClick(position);
            }
        });
    }

    public void bindOnClick(final RedditPost obj,final OnItemClick callback){
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(callback != null)
                callback.onItemClick(obj);
        }
    });
    }
}
