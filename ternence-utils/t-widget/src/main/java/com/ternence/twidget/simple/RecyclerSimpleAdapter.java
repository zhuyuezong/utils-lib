package com.ternence.twidget.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name: ternence-utils
 * File Name:    RecyclerSimpleAdapter.java
 * ClassName:    RecyclerSimpleAdapter
 *
 * Description: simple adapter for RecyclerView
 *
 */
public abstract class RecyclerSimpleAdapter<T, VH extends RecyclerSimpleViewHolder<T>> extends RecyclerView.Adapter
{
    public static final int TYPE_HEADER = -1;

    private RecyclerSimpleViewHolder mHeaderView;

    private Context mContext;

    private List<T> mDatas;

    private OnItemClickListener<VH> mItemClickListener;

    private OnItemLongClickListener<VH> mItemLongClickListener;

    public RecyclerSimpleAdapter(@NonNull Context context)
    {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }

    public RecyclerSimpleAdapter(@NonNull Context context, List<T> datas)
    {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        if (datas != null)
        {
            mDatas.addAll(datas);
        }
    }

    public List<T> getDatas()
    {
        return mDatas;
    }

    public void setHeaderView(RecyclerSimpleViewHolder viewHolder)
    {
        this.mHeaderView = viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener<VH> onItemClickListener)
    {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<VH> onItemLongClickListener)
    {
        this.mItemLongClickListener = onItemLongClickListener;
    }

    protected RecyclerSimpleViewHolder getHeaderView()
    {
        return mHeaderView;
    }

    @Override
    public RecyclerSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final RecyclerSimpleViewHolder viewHolder;
        if (viewType == TYPE_HEADER)
        {
            viewHolder = getHeaderView();
        }
        else
        {
            View itemView = LayoutInflater.from(mContext)
                                          .inflate(getItemLayoutID(viewType), parent, false);
            viewHolder = createItemViewHolder(itemView,viewType);
            if (mItemClickListener != null)
            {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        mItemClickListener.onItemClick((VH) viewHolder);
                    }
                });
            }
            if (mItemLongClickListener != null)
            {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View view)
                    {
                        return mItemLongClickListener.onLongClick((VH) viewHolder);
                    }
                });
            }
        }
        return viewHolder;
    }

    protected abstract VH createItemViewHolder(View itemView, int viewType);

    protected abstract int getItemLayoutID(int viewType);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (mHeaderView != null )
        {
            if (position != 0)
            {
                onBindItemViewHolder((VH) holder, position-1);
            }
        }
        else
        {
            onBindItemViewHolder((VH) holder, position);
        }
    }

    protected abstract void onBindItemViewHolder(VH holder, int index);

    @Override
    public int getItemCount()
    {
        if (getHeaderView() != null)
        {
            return mDatas.size() + 1;
        }
        return mDatas.size();
    }

    protected Context getContext()
    {
        return mContext;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder)
    {
        if (holder instanceof RecyclerSimpleViewHolder)
        {
            ((RecyclerSimpleViewHolder) holder).onViewRecycled();
        }
    }

    public interface OnItemClickListener<VH>
    {
        void onItemClick(VH vh);
    }

    public interface OnItemLongClickListener<VH>
    {
        boolean onLongClick(VH vh);
    }
}
