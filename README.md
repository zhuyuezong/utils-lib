## t-utils
导入方法
```Java
compile 'com.ternence.tutils:t-utils:1.0.1'
```
此库只是一些简单的工具类，后续会不断完善。

## t-widget
导入方法
```Java
compile 'com.ternence.twidget:t-widget:1.0.2'
```
此库主要对一些控件的封装与实现

### RecyclerView
#### RecyclerSimpleAdapter
主要实现在于Adapter的使用,普通列表需要继承RecyclerSimpleAdapter
```Java
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
```
RecyclerSimpleAdapter<String,ViewHolder> ,带有两个泛型类型，第一个是数据类型，第二个则是要实现的ViewHolder
#### RecyclerSectionAdapter
带有Section的列表需要继承RecyclerSectionAdapter
```Java
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
```
#### RecyclerSimpleViewHolder来实现，在setData方法中实现数据绑定
ViewHolder继承RecyclerSimpleViewHolder来实现，在setData方法中实现数据绑定
```Java
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
```
#### 点击事件实现
直接看demo中实现
```Java
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
```
## 其他控件
RoundImageView带圆角的imageView实现，SelectorImageView带点击效果的imageView实现
