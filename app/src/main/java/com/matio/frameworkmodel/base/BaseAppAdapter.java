package com.matio.frameworkmodel.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Angel on 2016/3/14.
 */
public abstract class BaseAppAdapter<T> extends BaseAdapter {

    public List<T> mList;

    public LayoutInflater mInflater;

    public Context mContext;

    public BaseAppAdapter(Context context, List list) {

        this.mList = list;

        this.mContext = context;

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return mList != null ? mList.size() : 0;

    }

    @Override
    public Object getItem(int position) {

        return mList != null ? mList.get(position) : null;

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getItemView(position, convertView, parent);

    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);
}
