package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.BasePostListActivity;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Calendar;
import java.util.List;

/**
 * Created by levinhtxbt@gmail.com on 31/08/2016.
 */
public class PostListRecyclerAdapter extends
        RecyclerView.Adapter<PostListRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_COUNT = 2, DEFAULT_ITEM = 0, BOTTOM_ITEM = 1;
    List<RedditPost> mListRedditPost;
    Context mContext;
    int mOrientation;
    OnItemClick callback;

    public PostListRecyclerAdapter(Context context, OnItemClick callback, int orientation) {
        this.mContext = context;
        this.callback = callback;
        this.mOrientation = orientation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType == DEFAULT_ITEM ?
                                R.layout.post_list_item_cardview : R.layout.footer_list_view,
                        parent, false);
        view.findViewById(R.id.item_clickable).setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RedditPost post = getItem(position);
        if (post != null) {
            holder.bindData(mContext, post, mOrientation);
        }
        holder.findViewById(R.id.item_clickable).setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 ?
                BOTTOM_ITEM : DEFAULT_ITEM;
    }

    public void setRedditPost(List<RedditPost> objs) {
        this.mListRedditPost = objs;
        notifyDataSetChanged();
    }

    public List<RedditPost> getListRedditPost() {
        return mListRedditPost;
    }

    @Override
    public int getItemCount() {
        return mListRedditPost == null ?
                0 : mListRedditPost.size() + 1;
    }

    public RedditPost getItem(int position) {
        return mListRedditPost == null || position == getItemCount() - 1 ?
                null : mListRedditPost.get(position);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        RedditPost post = getItem(position);
        String url = (post == null) ?
                "https://www.reddit.com/r/androiddev" : post.getUrl();
        callback.onItemClick(url);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView lblFirst;
        TextView lblSecond;
        TextView lblThird;
        TextView lblScore;

        public ViewHolder(View itemView) {
            super(itemView);
            lblFirst = (TextView) itemView.findViewById(R.id.lblFirst);
            lblSecond = (TextView) itemView.findViewById(R.id.lblSecond);
            lblThird = (TextView) itemView.findViewById(R.id.lblThird);
            lblScore = (TextView) itemView.findViewById(R.id.lblScore);
        }

        public void bindData(Context context, RedditPost obj, int orientation) {
            if (orientation == BasePostListActivity.ORIENTATION_PORTRAIT) {
                this.lblFirst.setText(Html.fromHtml("<big><font color=\"grey\">" + obj.getScore() + "<font></big> <b><font color=\"#0A295A\">" + obj.getAuthor() + "</font></b> <font color=\"black\">in</font> <b><font color=\"#0A295A\">" + obj.getSubreddit() + "</font></b>"));
                if (obj.isStickyPost()) {
                    lblSecond.setText(obj.getTitle());
                    lblSecond.setTextColor(ContextCompat.getColor(context, R.color.colorPostTitle_isSticky));
                } else {
                    lblSecond.setText(obj.getTitle());
                    lblSecond.setTextColor(Color.BLACK);
                }
            } else {
                lblFirst.setText(Html.fromHtml("<b><font color=\"#0A295A\">" + obj.getAuthor() + "</font></b>"));
                lblScore.setText(String.valueOf(obj.getScore()));
                lblScore.setTextColor(Color.GRAY);
                if (obj.isStickyPost()) {
                    lblSecond.setText(obj.getTitle());
                    lblSecond.setTextColor(ContextCompat.getColor(context, R.color.colorPostTitle_isSticky));
                } else {
                    lblSecond.setText(obj.getTitle());
                    lblSecond.setTextColor(Color.WHITE);
                }
            }
            long current = Calendar.getInstance().getTimeInMillis();
            long time = current - (obj.getCreatedUTC() * 1000);
            lblThird.setText(Html.fromHtml(obj.getCommentCount() + " Comments \u2022 "
                    + obj.getDomain() + " \u2022 " + getTime(time)));
            lblThird.setTextColor(Color.GRAY);
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

        public View findViewById(int id) {
            return itemView.findViewById(id);
        }

    }
}
