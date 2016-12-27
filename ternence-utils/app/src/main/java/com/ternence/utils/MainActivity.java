package com.ternence.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ternence.twidget.simple.OnItemClickListener;
import com.ternence.twidget.simple.OnItemLongClickListener;
import com.ternence.twidget.simple.RecyclerSectionAdapter;
import com.ternence.twidget.simple.RecyclerSimpleAdapter;
import com.ternence.twidget.simple.RecyclerSimpleViewHolder;
import com.ternence.twidget.widget.QuickRecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private QuickRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> string = new ArrayList<>();
        for (int i = 0; i < 20 ;i ++)
        {
            string.add(i + "..." + i);
        }
        Map<String,List<String>> map = new LinkedHashMap<>();
        map.put("SEC1",string);
        map.put("SEC2",string);
        mRecyclerView = (QuickRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this,string);
        MySectionAdapter sectionAdapter = new MySectionAdapter(this,map);
        sectionAdapter.setOnItemClickListener(new OnItemClickListener<ViewHolder>()
        {
            @Override
            public void onItemClick(ViewHolder viewHolder)
            {
                Toast.makeText(MainActivity.this,viewHolder.getData(),Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener<ViewHolder>()
        {
            @Override
            public boolean onLongClick(ViewHolder viewHolder)
            {
                Toast.makeText(MainActivity.this,viewHolder.getData(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mRecyclerView.setAdapter(sectionAdapter);
    }

    private static class ViewHolder extends RecyclerSimpleViewHolder<String>
    {
        public ViewHolder(View itemView)
        {
            super(itemView);
        }

        @Override
        protected void setData(String data)
        {
            super.setData(data);
            ((TextView)findViewById(R.id.text)).setText(data);
        }
    }

    private static class MyAdapter extends RecyclerSimpleAdapter<String,ViewHolder>
    {
        public MyAdapter(@NonNull Context context,List<String> string)
        {
            super(context,string);
        }

        @Override
        protected ViewHolder createItemViewHolder(View itemView, int viewType)
        {
            return new ViewHolder(itemView);
        }

        @Override
        protected int getItemLayoutID(int viewType)
        {
            return R.layout.view_holder;
        }
    }

    private static class MySectionAdapter extends RecyclerSectionAdapter<String,String,ViewHolder,ViewHolder>
    {
        public MySectionAdapter(@NonNull Context context, Map<String, List<String>> datas)
        {
            super(context, datas);
        }

        @Override
        protected int getSectionLayoutID()
        {
            return R.layout.view_holder;
        }

        @Override
        protected int getItemLayoutID(int viewType)
        {
            return R.layout.view_holder;
        }

        @Override
        protected ViewHolder createItemViewHolder(View itemView, int viewType)
        {
            return new ViewHolder(itemView);
        }

        @Override
        protected ViewHolder createSectionView(View itemView)
        {
            return new ViewHolder(itemView);
        }
    }
}
