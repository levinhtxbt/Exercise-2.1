package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.PostListActivity;
import com.hasbrain.areyouandroiddev.PostViewActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Levin on 08/04/2016.
 */
public class PostInSectionAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mListGroup;
    private HashMap<String, List<RedditPost>> mListChild;
    private int mLayoutIDGroup;
    private int mLayoutIDChild;
    private int mScreenMode;

    public PostInSectionAdapter(Context mContext,
                                int mLayoutIDGroup, int mLayoutIDChild,
                                List<String> mListGroup, HashMap<String, List<RedditPost>> mListChild,
                                int mScreenMode) {
        this.mContext = mContext;
        this.mListGroup = mListGroup;
        this.mListChild = mListChild;
        this.mLayoutIDGroup = mLayoutIDGroup;
        this.mLayoutIDChild = mLayoutIDChild;
        this.mScreenMode = mScreenMode;
    }

    @Override
    public int getGroupCount() {
        return mListGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListChild.get(mListGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListChild.get(mListGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_post_in_sector, parent, false);
        }
        TextView tvTitleGroup = (TextView) convertView.findViewById(R.id.lblTitleGroupPostInSection);
        tvTitleGroup.setText(mListGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PostListViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.post_list_item, parent, false);
            viewHolder = new PostListViewHolder();
            viewHolder.lblFirst = (TextView) convertView.findViewById(R.id.lblFirst);
            viewHolder.lblSecond = (TextView) convertView.findViewById(R.id.lblSecond);
            viewHolder.lblThird = (TextView) convertView.findViewById(R.id.lblThird);
            viewHolder.lblScore = (TextView) convertView.findViewById(R.id.lblScore);
            convertView.setTag(viewHolder);
        } else viewHolder = (PostListViewHolder) convertView.getTag();

        //Bind data
        RedditPost obj = mListChild.get(mListGroup.get(groupPosition)).get(childPosition);
        viewHolder.bindData(obj, mScreenMode);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
