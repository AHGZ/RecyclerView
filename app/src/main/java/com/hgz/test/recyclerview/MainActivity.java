package com.hgz.test.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.hgz.test.recyclerview.adapter.MyAdapter;
import com.hgz.test.recyclerview.view.MyDecoration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Button button = (Button) findViewById(R.id.button);
        Button btnSelectedAll = (Button) findViewById(R.id.btnSelectedAll);
        Button btnReverseSelected = (Button) findViewById(R.id.btnReverseSelected);
        //RecyclerView的展示样式
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this, 3);
        //设置以瀑布流形式展示
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //设置RecyclerView的展示样式
        recyclerView.setLayoutManager(linearLayoutManager);

        //gridview的形式展示,可以通过setSpanSizeLookup 来自定义每个item占的列数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3 - position % 3;
            }
        });

        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        //在条目之间增添横线
        recyclerView.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL));
        //使用单击事件接口，完成添加条目的操作
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                myAdapter.addItem(position);
            }
        });
        //使用长按事件接口，完成修改条目的操作
        myAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View v, int position) {
                myAdapter.updateItem(position);
            }
        });
        button.setOnClickListener(this);
        btnSelectedAll.setOnClickListener(this);
        btnReverseSelected.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //if和elseif是有先后顺序的,先判断范围小的,然后再判断范围大的,
                //因为GridLayoutManager 是继承 LinearLayoutManager ,所以他本质上也是LinearLayoutManager,
                //所以不能先判断是否是LinearLayoutManager (LinearLayoutManager范围大)
                if (layoutManager instanceof GridLayoutManager) {
                    recyclerView.setLayoutManager(linearLayoutManager);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
                break;
            //控制RecyclerView列表中的CheckBox的全选
            case R.id.btnSelectedAll:
                myAdapter.selectedAll();
                break;
            //控制RecyclerView列表中的CheckBox的反选
            case R.id.btnReverseSelected:
                myAdapter.reverseSelected();
                break;
        }

    }

}

