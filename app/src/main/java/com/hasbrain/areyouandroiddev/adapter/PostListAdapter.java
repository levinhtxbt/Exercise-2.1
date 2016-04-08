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
            viewHolder.lblScore = (TextView)convertView.findViewById(R.id.lblScore);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PostListViewHolder) convertView.getTag();
        }
        throwItem(mContext,viewHolder, mListRedditPost.get(position),mScreenMode);
        return convertView;
    }

    public static void throwItem(Context mContext,PostListViewHolder viewholder, RedditPost obj, int mScreenMode) {

        if (mScreenMode == PostListActivity.ORIENTATION_PORTRAIT) {
            viewholder.lblFirst.setText(Html.fromHtml("<big><font color=\"grey\">" + obj.getScore() + "<font></big> <b><font color=\"#0A295A\">" + obj.getAuthor() + "</font></b> <font color=\"black\">in</font> <b><font color=\"#0A295A\">" + obj.getSubreddit() + "</font></b>"));
            if (obj.isStickyPost()) {
                viewholder.lblSecond.setText(obj.getTitle());
                viewholder.lblSecond.setTextColor(ContextCompat.getColor(mContext, R.color.colorPostTitle_isSticky));
            } else {
                viewholder.lblSecond.setText(obj.getTitle());
                viewholder.lblSecond.setTextColor(Color.BLACK);
            }
        } else {
            viewholder.lblFirst.setText(Html.fromHtml("<b><font color=\"#0A295A\">" + obj.getAuthor() + "</font></b>"));
            viewholder.lblScore.setText(String.valueOf(obj.getScore()));
            viewholder.lblScore.setTextColor(Color.GRAY);
            if (obj.isStickyPost()) {
                viewholder.lblSecond.setText(obj.getTitle());
                viewholder.lblSecond.setTextColor(ContextCompat.getColor(mContext, R.color.colorPostTitle_isSticky));
            } else {
                viewholder.lblSecond.setText(obj.getTitle());
                viewholder.lblSecond.setTextColor(Color.WHITE);
            }
        }
        long current = Calendar.getInstance().getTimeInMillis();
        long time = current - (obj.getCreatedUTC() * 1000);
        viewholder.lblThird.setText(Html.fromHtml(obj.getCommentCount() + " Comments \u2022 " + obj.getDomain() + " \u2022 " + getTime(time)));
        viewholder.lblThird.setTextColor(Color.GRAY);
    }

    public static String getTime(long millis) {
        int[] time = {1000, 60, 60, 24, 30, 12};
        String strTime = "";
        int i;
        for (i = 0; i < time.length - 1; i++) {
            millis /= time[i];
            if (millis < time[i + 1])
                break;
        }
        switch (i) {
            case 1:
                strTime = "seconds";
                break;
            case 2:
                strTime = "minutes";
                break;
            case 3:
                strTime = "days";
                break;
            case 4:
                strTime = "months";
                break;
            case 5:
                strTime = "year";
                break;
        }
        return millis + " " + strTime.toString();

//        long days = TimeUnit.MILLISECONDS.toDays(millis);
//        millis -= TimeUnit.DAYS.toMillis(days);
//        long hours = TimeUnit.MILLISECONDS.toHours(millis);
//        millis -= TimeUnit.HOURS.toMillis(hours);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
//        millis -= TimeUnit.MINUTES.toMillis(minutes);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
//        return days+" days";
    }
}

