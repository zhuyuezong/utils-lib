package com.ternence.twidget.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name: ternence-utils
 * File Name:    RecyclerSectionAdapter.java
 * ClassName:    RecyclerSectionAdapter
 *
 * Description: adapter with title
 *
 * @author Ternence.Zhu
 */
public abstract class RecyclerSectionAdapter<K, T, SH extends RecyclerSimpleViewHolder, VH extends RecyclerSimpleViewHolder> extends RecyclerView.Adapter
{
    public static final int TYPE_HEADER = -1;

    public static final int TYPE_SECTION = -2;

    public static final int TYPE_NORMAL = 0;

    private final Map<K,List<T>> mDatas;

    private Context mContext;

    private RecyclerSimpleViewHolder mHeaderView;

    private OnItemClickListener<VH> mItemClickListener;

    private OnItemLongClickListener<VH> mItemLongClickListener;

    private final List<Integer> mIndicesIndex;

    private final List<K> mSectionDatas;

    public RecyclerSectionAdapter(@NonNull Context context)
    {
        this(context,null);
    }

    public RecyclerSectionAdapter(@NonNull Context context,Map<K,List<T>> datas)
    {
        this.mContext = context;
        this.mDatas = new LinkedHashMap<>();
        this.mIndicesIndex = new ArrayList<>();
        this.mSectionDatas = new ArrayList<>();
        if (datas != null)
        {
            mDatas.putAll(datas);
            setupIndices();
        }
    }

    protected void setupIndices()
    {
        mIndicesIndex.clear();
        mSectionDatas.clear();
        mIndicesIndex.add(getHeaderView() == null ? 0: 1);
        for (K key: this.mDatas.keySet())
        {
            mIndicesIndex.add(1 + mIndicesIndex.get(mIndicesIndex.size() - 1) + this.mDatas.get(key)
                                                                                           .size());
        }
        mIndicesIndex.remove(mIndicesIndex.size() - 1);

        mSectionDatas.addAll(mDatas.keySet());
    }

    public void setHeaderView(RecyclerSimpleViewHolder viewHolder)
    {
        this.mHeaderView = viewHolder;
    }

    protected RecyclerSimpleViewHolder getHeaderView()
    {
        return mHeaderView;
    }


    public void setOnItemClickListener(OnItemClickListener<VH> onItemClickListener)
    {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<VH> onItemLongClickListener)
    {
        this.mItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public final RecyclerSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final RecyclerSimpleViewHolder viewHolder;
        switch (viewType)
        {
            case TYPE_HEADER:
                viewHolder = getHeaderView();
                break;
            case TYPE_SECTION:
                View sectionView = LayoutInflater.from(mContext).inflate(getSectionLayoutID(),parent,false);
                viewHolder = createSectionView(sectionView);
                break;
            case TYPE_NORMAL:
                View itemView = LayoutInflater.from(mContext).inflate(getItemLayoutID(viewType),parent,false);
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
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case TYPE_SECTION:
                K sectionData = mSectionDatas.get(mIndicesIndex.indexOf(position));
                ((RecyclerSimpleViewHolder)holder).setData(sectionData);
                onBindSectionView((SH) holder, sectionData);
                break;
            case TYPE_NORMAL:
                T data = getItemData(position);
                ((RecyclerSimpleViewHolder)holder).setData(data);
                onBindItemViewHolder((VH) holder, data);
                break;
        }
    }

    private T getItemData(int position)
    {
        for (int index = 0; index < mIndicesIndex.size(); index ++)
        {
            if (mIndicesIndex.get(index) > position)
            {
                K sectionData = mSectionDatas.get(index - 1);
                return mDatas.get(sectionData).get(position - mIndicesIndex.get(index - 1) -1);
            }
        }
        K sectionData = mSectionDatas.get(mSectionDatas.size() -1);

        int index = position - mIndicesIndex.get(mSectionDatas.size() - 1) - 1;
        if (index >= 0 && index < mDatas.get(sectionData).size())
        {
            return mDatas.get(sectionData).get(index);
        }
        return null;
    }

    @Override
    public int getItemCount()
    {
        int count  = 0;
        if (getHeaderView() != null)
        {
            count ++;
        }
        for (K key: mDatas.keySet())
        {
            count += mDatas.get(key).size();
        }
        count += mIndicesIndex.size();
        return count;
    }

    @Override
    public final int getItemViewType(int position)
    {
        if (isHeader(position))
        {
            return TYPE_HEADER;
        }
        else if (isScetion(position))
        {
            return TYPE_SECTION;
        }
        else
        {
            return TYPE_NORMAL;
        }
    }

    /**
     * replace all data and refresh list
     * @param datas datas
     */
    public void replaceAll(Map<K,List<T>> datas)
    {
        this.mDatas.clear();
        if (datas != null)
        {
            datas.putAll(datas);
        }
        notifyDataSetChanged();
    }

    private boolean isScetion(int position)
    {
        return mIndicesIndex.contains(position);
    }

    private boolean isHeader(int position)
    {
        return (getHeaderView() !=null && position == 0);
    }

    protected abstract int getSectionLayoutID();

    protected abstract int getItemLayoutID(int viewType);

    protected abstract VH createItemViewHolder(View itemView, int viewType);

    protected abstract SH createSectionView(View itemView);

    protected void onBindSectionView(SH holder, K sectionData)
    {

    }

    protected void onBindItemViewHolder(VH holder, T data)
    {

    }
}
