package com.ternence.twidget.simple;

/**
 * Project Name: ternence-utils
 * File Name:    OnItemLongClickListener.java
 * ClassName:    OnItemLongClickListener
 *
 * Description: item long click listener of RecyclerView
 *
 * @author Ternence.Zhu
 */
public interface OnItemLongClickListener<VH>
{
    boolean onLongClick(VH vh);
}
