package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.activity.BasePostListActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Levin on 03/04/2016.
 */
public class PostListAdapter extends ArrayAdapter<RedditPost> implements View.OnClickListener {
    private static final int TYPE_COUNT = 2, DEFAULT_ITEM = 0, BOTTOM_ITEM = 1;
    Context mContext;
    int mLayoutID;
    List<RedditPost> mListRedditPost;
    int mScreenMode;
    OnItemClick callback;
    boolean[] animationStates;

    public PostListAdapter(Context context, int resource, List<RedditPost> objects, OnItemClick callback, int screen_mode) {
        super(context, 0);
        this.mContext = context;
        this.mLayoutID = resource;
        this.mScreenMode = screen_mode;
        this.callback = callback;
        setPostList(objects);
    }

    public PostListAdapter(Context context, int resource, OnItemClick callback, int screen_mode) {
        this(context, resource, null, callback, screen_mode);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostListViewHolder viewHolder = null;
        RedditPost item = getItem(position);
        int type = getItemViewType(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(type == DEFAULT_ITEM ?
                    R.layout.post_list_item : R.layout.footer_list_view, null);
            convertView.findViewById(R.id.item_clickable).setOnClickListener(this);

            if (type == DEFAULT_ITEM) {
                viewHolder = new PostListViewHolder();
                viewHolder.lblFirst = (TextView) convertView.findViewById(R.id.lblFirst);
                viewHolder.lblSecond = (TextView) convertView.findViewById(R.id.lblSecond);
                viewHolder.lblThird = (TextView) convertView.findViewById(R.id.lblThird);
                viewHolder.lblScore = (TextView) convertView.findViewById(R.id.lblScore);
                convertView.setTag(viewHolder);
            }
            int sumHeight = sumChildrenHeight(parent);

            if (mScreenMode==BasePostListActivity.ORIENTATION_PORTRAIT && !animationStates[position] && sumHeight <= parent.getHeight()) {
                animationStates[position] = true;
                convertView.animate().alpha(0).translationYBy(80f).setDuration(0).start();
                convertView.animate().alpha(1).translationYBy(-80f).setDuration(800)
                        .setInterpolator(new DecelerateInterpolator())
                        .setStartDelay(position == getCount() - 1 ?
                                0 : position * 200)
                        .start();
            }
        }


        if (type == DEFAULT_ITEM && item != null) {
            viewHolder = (PostListViewHolder) convertView.getTag();
            throwItem(mContext, viewHolder, mListRedditPost.get(position), mScreenMode);
        }

        convertView.findViewById(R.id.item_clickable).setTag(position);
        return convertView;
    }

    @Override
    public int getCount() {
        return this.mListRedditPost == null ? 0 : mListRedditPost.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? BOTTOM_ITEM : DEFAULT_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public static void throwItem(Context mContext, PostListViewHolder viewholder, RedditPost obj, int mScreenMode) {

        if (mScreenMode == BasePostListActivity.ORIENTATION_PORTRAIT) {
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

    @Override
    public RedditPost getItem(int position) {
        return mListRedditPost == null || position == getCount() - 1 ?
                null : mListRedditPost.get(position);
    }

    public void setPostList(List<RedditPost> posts) {
        this.mListRedditPost = posts;
        int count = getCount();
        animationStates = new boolean[count];
        notifyDataSetChanged();
    }

    public List<RedditPost> getPostList() {
        return this.mListRedditPost;
    }

    int sumChildrenHeight(ViewGroup v) {
        int total = 0;
        for (int i = 0; i < v.getChildCount(); i++)
            total += v.getChildAt(i).getHeight();
        return total;
    }

    @Override
    public void onClick(View v) {
        int postition = (int) v.getTag();
        RedditPost post = getItem(postition);
        String url = (post == null) ?
                "https://www.reddit.com/r/androiddev" : post.getUrl();
        callback.onItemClick(url);
    }
}

