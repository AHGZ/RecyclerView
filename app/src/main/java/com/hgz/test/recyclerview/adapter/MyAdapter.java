package com.hgz.test.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgz.test.recyclerview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> data;
    HashMap<Integer, Boolean> isCheckedHashMap;

    public MyAdapter() {
        isCheckedHashMap = new HashMap<>();
        data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("这是条目" + i);
            isCheckedHashMap.put(i, false);
        }
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    //定义接口
    //单击事件的接口
    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

    //长按事件的接口
    public interface OnItemLongClickListener {
        void OnItemLongClick(View v, int position);
    }

    //设置单击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置长按事件的接口
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    //创建布局和viewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
                holder = new MyViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view2, parent, false);
                holder = new MyViewHolder2(view);
                break;
        }

        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //        if (position%2==1){
//            getImage(data.get(position).getThumbnail_pic_s(),holder.showImage);
//        }
        switch (getItemViewType(position)) {
            case 0:
                MyViewHolder holder1 = (MyViewHolder) holder;
                holder1.showImage.setImageResource(R.drawable.bagua);
                holder1.showText.setText(data.get(position));
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //暴露一个单击回调接口
                        if (onItemClickListener != null) {
                            onItemClickListener.OnItemClick(v, position);
                        }
                    }
                });
                holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (onItemLongClickListener != null) {
                            onItemLongClickListener.OnItemLongClick(v, position);
                        }
                        return true;
                    }
                });
                holder1.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isCheckedHashMap.put(position, !isCheckedHashMap.get(position));
                        notifyDataSetChanged();

                        //调用单选方法
                        //singleSelected(position);
                    }
                });
                holder1.checkBox.setChecked(isCheckedHashMap.get(position));
                break;
            case 1:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;
                holder2.item2_iv.setImageResource(R.drawable.xing);
                holder2.item2_tv.setText(data.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView showImage;
        private final TextView showText;
        private final CheckBox checkBox;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            showImage = (ImageView) itemView.findViewById(R.id.showImage);
            showText = (TextView) itemView.findViewById(R.id.showText);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            this.itemView = itemView;
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private final ImageView item2_iv;
        private final TextView item2_tv;

        public MyViewHolder2(View itemView) {
            super(itemView);
            item2_iv = (ImageView) itemView.findViewById(R.id.item2_Iv);
            item2_tv = (TextView) itemView.findViewById(R.id.item2_Tv);
        }
    }

    //增加条目的方法
    public void addItem(int position) {
        data.add(position + 1, "这是新添加的条目");
        notifyItemRangeChanged(position + 1, getItemCount());
    }

    //删除条目的方法
    public void deleteItem(int position) {
        data.remove(position);
        notifyItemRangeRemoved(position, getItemCount());
    }

    //修给条目的方法
    public void updateItem(int position) {
        data.remove(position);
        data.add(position, "这是修改的条目");
        notifyItemChanged(position);
    }

    //全选的方法
    public void selectedAll() {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();
        //如果发现有没有选中的item,我就应该去全部选中,这个变量就应该设置成true,否则就是false
        boolean shouldSelectedAll = false;

        //这个for循环就是判断一下接下来要全部选中,还是全部不选中
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            //如果有没选中的,那就去全部选中 ,如果发现全都选中了那就全部不选中,
            if (!value) {
                shouldSelectedAll = true;
                break;
            }
        }
        //如果shouldSelectedAll为true说明需要全部选中,
        // 如果为false说明没有没有选中的,已经是是全部选中的状态,需要全部不选中
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldSelectedAll);
        }
        notifyDataSetChanged();
    }

    //反选
    public void reverseSelected() {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(!entry.getValue());
        }
        notifyDataSetChanged();
    }

    //单选 点击checkBox的时候只选中当前的item,在checkBox的点击事件中调用
    public void singleSelected(int position) {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        isCheckedHashMap.put(position, true);
        notifyDataSetChanged();
    }

}
