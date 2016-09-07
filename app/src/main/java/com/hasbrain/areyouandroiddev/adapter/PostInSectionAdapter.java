package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Levin on 08/04/2016.
 */
public class PostInSectionAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    private static final int GROUP_TYPE_COUNT = 2, TYPE_DEFAULT = 0, TYPE_BOTTOM_VIEW = 1;

    private Context mContext;
    private List<String> mListGroup;
    private HashMap<String, List<RedditPost>> mListChild;
    private int mLayoutIDGroup;
    private int mLayoutIDChild;
    private int mScreenMode;
    OnItemClick callback;

    public PostInSectionAdapter(Context mContext,
                                int mLayoutIDGroup, int mLayoutIDChild,
                                List<String> mListGroup, HashMap<String, List<RedditPost>> mListChild,
                                OnItemClick callback,
                                int mScreenMode) {
        this.mContext = mContext;
        this.mListGroup = mListGroup;
        if (mListChild == null)
            this.mListChild = new HashMap<>();
        else
            this.mListChild = mListChild;
        this.mLayoutIDGroup = mLayoutIDGroup;
        this.mLayoutIDChild = mLayoutIDChild;
        this.callback = callback;
        this.mScreenMode = mScreenMode;
    }

    @Override
    public int getGroupCount() {
        return mListGroup.size();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return groupPosition == mListGroup.size() - 1 ?
                TYPE_BOTTOM_VIEW : TYPE_DEFAULT;
    }

    @Override
    public int getGroupTypeCount() {
        return GROUP_TYPE_COUNT;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListChild.get(mListGroup.get(groupPosition)) == null ?
                0 : mListChild.get(mListGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<RedditPost> child = mListChild.get(mListGroup.get(groupPosition));
        return child == null ?
                null : child.get(childPosition);
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
        int type = getGroupType(groupPosition);
        String title = mListGroup.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == TYPE_DEFAULT) {
                convertView = infalInflater.inflate(R.layout.group_post_in_sector, null);
            } else {
                convertView = infalInflater.inflate(R.layout.footer_list_view, null);
                convertView.findViewById(R.id.item_clickable).setTag(new int[]{groupPosition, 0});
                convertView.findViewById(R.id.item_clickable).setOnClickListener(this);
            }
        }
        if (type == TYPE_DEFAULT) {
            TextView tvTitleGroup = (TextView) convertView.findViewById(R.id.tvTitleGroupPostInSection);
            tvTitleGroup.setText(title);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PostListViewHolder viewHolder = null;
        RedditPost child = mListChild.get(mListGroup.get(groupPosition)).get(childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.post_list_item, parent, false);
            viewHolder = new PostListViewHolder();
            viewHolder.lblFirst = (TextView) convertView.findViewById(R.id.lblFirst);
            viewHolder.lblSecond = (TextView) convertView.findViewById(R.id.lblSecond);
            viewHolder.lblThird = (TextView) convertView.findViewById(R.id.lblThird);
            viewHolder.lblScore = (TextView) convertView.findViewById(R.id.lblScore);
            convertView.findViewById(R.id.item_clickable).setOnClickListener(this);
            convertView.setTag(viewHolder);
        }

        if (viewHolder == null)
            viewHolder = (PostListViewHolder) convertView.getTag();
        PostListAdapter.throwItem(mContext, viewHolder, child, mScreenMode);
        convertView.findViewById(R.id.item_clickable).setTag(new int[]{groupPosition, childPosition});
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public HashMap<String, List<RedditPost>> getChildList() {
        return mListChild;
    }

    @Override
    public void onClick(View v) {
        int[] position = (int[]) v.getTag();
        RedditPost post = (RedditPost) getChild(position[0], position[1]);
        String url = post == null
                ? "https://www.reddit.com/r/androiddev" : post.getUrl();
        callback.onItemClick(url);
    }
}
