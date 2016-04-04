package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    public PostListAdapter(Context context, int resource, List<RedditPost> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayoutID = resource;
        this.mListRedditPost = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostListViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
            //convertView.setBackgroundResource(R.drawable.customsharp);
            viewHolder = new PostListViewHolder();
            viewHolder.lblFirst = (TextView) convertView.findViewById(R.id.lblFirst);
            viewHolder.lblSecond = (TextView) convertView.findViewById(R.id.lblSecond);
            viewHolder.lblThird = (TextView) convertView.findViewById(R.id.lblThird);
            viewHolder.lblScore = (TextView) convertView.findViewById(R.id.lblScore);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PostListViewHolder) convertView.getTag();
        }
        throwItem(viewHolder, mListRedditPost.get(position));
        return convertView;
    }

    public void throwItem(PostListViewHolder viewholder, RedditPost obj) {
        viewholder.lblScore.setText(Html.fromHtml("<font color=\"grey\">" + obj.getScore() + "<font>"));
        viewholder.lblFirst.setText(Html.fromHtml("<b><font color=\"#0A295A\">" + obj.getAuthor() + "</font></b> <font color=\"black\">in</font> <b><font color=\"#0A295A\">" + obj.getSubreddit() + "</font></b>"));

        if (obj.isStickyPost()) {
            viewholder.lblSecond.setText(Html.fromHtml("<font color=\"#387801\">" + obj.getTitle() + "</font>"));
        } else {
            viewholder.lblSecond.setText(Html.fromHtml(obj.getTitle()));
        }
        long current = Calendar.getInstance().getTimeInMillis();
        long time = current - (obj.getCreatedUTC() * 1000);
        viewholder.lblThird.setText(Html.fromHtml("<font color=\"grey\">" + obj.getCommentCount() + " Comments \u2022 " + obj.getDomain() + " \u2022 " + getTime(time) + "</font>"));
    }

    public String getTime(long millis) {
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

