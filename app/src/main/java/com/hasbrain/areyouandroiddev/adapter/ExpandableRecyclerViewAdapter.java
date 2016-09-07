package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.graphics.Color;
import android.nfc.tech.IsoDep;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Levin on 19/04/2016.
 */
public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener {
    private static final String LOG = "Expanable Adapter";
    public static final int TYPE_HEADER = 0, TYPE_CHILD = 1, TYPE_FOOTER = 2;

    private Context mContext;
    private int mLayoutID;
    private List<String> mParents;
    HashMap<String, List<RedditPost>> mapParent_Child;
    HashMap<String, Boolean> isExpandable;
    LayoutInflater inflater;
    int mScreenMode;
    OnItemClick callback;

    public ExpandableRecyclerViewAdapter(Context Context, List<String> Parent, HashMap<String, List<RedditPost>> mapParent_Child, int ScreenMode) {
        this.mContext = Context;
        this.mParents = Parent;
        this.mapParent_Child = mapParent_Child;
        isExpandable = new HashMap<>();
        for (String parent : mParents) {
            isExpandable.put(parent, false);
        }
        inflater = LayoutInflater.from(mContext);
        this.mScreenMode = ScreenMode;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = inflater.inflate(R.layout.group_post_in_sector, parent, false);
                break;
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.footer_list_view, parent, false);
                view.setOnClickListener(this);
                break;
            default:
                view = inflater.inflate(R.layout.post_list_item, parent, false);
                break;
        }
        return new RecyclerViewHolder(view, viewType != TYPE_CHILD);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (position < getItemCount() - 1) {
            int[] convertPosition = convertPosition(position);
            if (convertPosition[1] == -1) {
                holder.lblHeader.setText(mParents.get(convertPosition[0]));
                holder.lblHeader.setTag(convertPosition[0]);
                holder.lblHeader.setOnClickListener(this);
            } else {
                String parentName = mParents.get(convertPosition[0]);
                RedditPost child = mapParent_Child.get(parentName).get(convertPosition[1]);
                //Bind data
                holder.bindData(child, mScreenMode);
                holder.bindOnClick(child, callback);
            }
        }
    }

    @Override
    public int getItemCount() {
        int numParent = mParents.size();
        int numChild = 0;
        for (int i = 0; i < mParents.size(); i++) {
            if (isExpandable.get(mParents.get(i)))
                numChild += mapParent_Child.get(mParents.get(i)).size();
        }
        return numChild + numParent + 1; // include position footer
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            int[] convertPosition = convertPosition(position);
            if (convertPosition[1] == -1)
                return TYPE_HEADER;
            else return TYPE_CHILD;
        }
    }

    public int[] convertPosition(int position) {
        int tmp = 0;
        int parentIndex = -1, childIndex = -1;
        for (int i = 0; i < mParents.size(); i++) {
            if (position == tmp) {
                parentIndex = i;
                childIndex = -1;
                break;
            } else {
                if (isExpandable.get(mParents.get(i))) {
                    int count = mapParent_Child.get(mParents.get(i)).size();
                    if (position <= tmp + count) {
                        parentIndex = i;
                        childIndex = position - tmp - 1;
                        break;
                    } else
                        tmp += count + 1;
                } else
                    tmp += 1;
            }
        }
        return new int[]{parentIndex, childIndex};
    }

    public int getPositionOfHeader(int headerIndex) {
        int position = 0;
        for (int i = 0; i < mParents.size(); i++) {
            if (i == headerIndex)
                return position;
            else {
                if (isExpandable.get(mParents.get(i))) {
                    position += mapParent_Child.get(mParents.get(i)).size() + 1;
                } else
                    position += 1;
            }
        }
        return position;
    }

    @Override
    public void onClick(View v) {
        if(v.getTag()!=null){
            int parentIndex = (int) v.getTag();
            String parentName = mParents.get(parentIndex);
            int position = getPositionOfHeader(parentIndex);

            if (isExpandable.get(parentName)) {
                isExpandable.put(parentName, false);
                notifyItemRangeRemoved(position + 1, mapParent_Child.get(parentName).size());
            } else {
                isExpandable.put(parentName, true);
                notifyItemRangeInserted(position + 1, mapParent_Child.get(parentName).size());
            }
        }else callback.onItemClick(TYPE_FOOTER);

    }

    public void setOnItemClick(OnItemClick click) {
        this.callback = click;
    }
}
