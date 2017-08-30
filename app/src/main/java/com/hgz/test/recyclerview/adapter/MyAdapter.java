package com.hgz.test.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgz.test.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<String> list;

    public MyAdapter(){
        list=new ArrayList<String>();
        for (int i=0;i<30;i++){
            list.add("这是条目"+i);
        }
    }
    //创建布局和viewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, null);
        return new MyViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.showImage.setImageResource(R.mipmap.ic_launcher);
        holder.showText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView showImage;
        private final TextView showText;

        public MyViewHolder(View itemView) {
            super(itemView);
            showImage = (ImageView) itemView.findViewById(R.id.showImage);
            showText = (TextView) itemView.findViewById(R.id.showText);
        }
    }
}
