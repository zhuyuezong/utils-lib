package com.ternence.twidget.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
/**
 * Project Name: ternence-utils
 * File Name:    QuickRecyclerView.java
 * ClassName:    QuickRecyclerView
 *
 * Description: override RecyclerView
 *
 * @author Ternence.Zhu
 */
public class QuickRecyclerView extends RecyclerView
{
    public QuickRecyclerView(Context context)
    {
        super(context);
    }

    public QuickRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public QuickRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public int getPosition(ViewHolder viewHolder)
    {
        return getChildAdapterPosition(viewHolder.itemView);
    }
}
