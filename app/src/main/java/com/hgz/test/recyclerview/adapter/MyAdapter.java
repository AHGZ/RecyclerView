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
    private List<String> data;

    public MyAdapter(){
       data= new ArrayList<String>();
       for (int i=0;i<30;i++){
           data.add("这是条目"+i);
       }
    }
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    //定义接口
    //单击事件的接口
    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }
    //长按事件的接口
    public interface  OnItemLongClickListener{
        void OnItemLongClick(View v,int position);
    }
    //设置单击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    //设置长按事件的接口
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    //创建布局和viewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        return new MyViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        if (position%2==1){
//            getImage(data.get(position).getThumbnail_pic_s(),holder.showImage);
//        }
        holder.showImage.setImageResource(R.drawable.bagua);
        holder.showText.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暴露一个单击回调接口
                if (onItemClickListener!=null) {
                    onItemClickListener.OnItemClick(v, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener!=null) {
                    onItemLongClickListener.OnItemLongClick(v, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView showImage;
        private final TextView showText;
        View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            showImage = (ImageView) itemView.findViewById(R.id.showImage);
            showText = (TextView) itemView.findViewById(R.id.showText);
            this.itemView=itemView;
        }
    }
    //增加条目的方法
    public void addItem(int position){
        data.add(position+1,"这是新添加的条目");
        notifyItemRangeChanged(position+1,getItemCount());
    }
    //删除条目的方法
    public void deleteItem(int position){
        data.remove(position);
        notifyItemRangeRemoved(position,getItemCount());
    }
    //修给条目的方法
    public void updateItem(int position){
        data.remove(position);
        data.add(position,"这是修改的条目");
        notifyItemChanged(position);
    }
}
