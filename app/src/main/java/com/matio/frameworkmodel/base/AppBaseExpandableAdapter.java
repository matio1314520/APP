package com.matio.frameworkmodel.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by Angel on 2016/3/15.
 */
public abstract class AppBaseExpandableAdapter<T> extends BaseExpandableListAdapter {

    public List<T> mGroupList;

    public Context mContext;

    public List<List<T>> mChildrenList;

    public LayoutInflater mInflater;

    public AppBaseExpandableAdapter(Context mContext, List<T> mGroupList, List<List<T>> mChildrenList) {
        this.mGroupList = mGroupList;
        this.mContext = mContext;
        this.mChildrenList = mChildrenList;

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildrenList != null ? mChildrenList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildrenList != null ? mChildrenList.get(groupPosition).get(childPosition) : null;
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return getGroupItemView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return getChildItemView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public abstract View getGroupItemView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    public abstract View getChildItemView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);
}