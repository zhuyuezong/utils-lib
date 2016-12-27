package com.ternence.twidget.simple;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Project Name: ternence-utils
 * File Name:    RecyclerSimpleViewHolder.java
 * ClassName:    RecyclerSimpleViewHolder
 *
 * Description: simple view holder
 */
public class RecyclerSimpleViewHolder<T> extends RecyclerView.ViewHolder
{
    private T mData;

    public RecyclerSimpleViewHolder(View itemView)
    {
        super(itemView);
    }

    public void setData(T data)
    {
        mData = data;
    }

    public void onViewRecycled()
    {

    }

    protected final View findViewById(@IdRes int id)
    {
        return itemView.findViewById(id);
    }
}
