package com.bawei.day1011qqlogowithqqshare.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.day1011qqlogowithqqshare.JavaBean.Bean;
import com.bawei.day1011qqlogowithqqshare.R;
import com.bawei.day1011qqlogowithqqshare.Utils.ImageLoaderUtius;

/**
 * Created by 张祺钒
 * on2017/10/11.
 */

public class ListViewAdapter extends BaseAdapter {
    private Bean bean;
    private Context context;

    public ListViewAdapter(Bean bean, Context context) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean.apk.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.listview_item,null);
            holder.imageView=convertView.findViewById(R.id.iv_item);
            holder.textView=convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ImageLoaderUtius.setImageView(bean.apk.get(position).iconUrl,context,holder.imageView);
        holder.textView.setText(bean.apk.get(position).categoryName);
        return convertView;
    }
    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
