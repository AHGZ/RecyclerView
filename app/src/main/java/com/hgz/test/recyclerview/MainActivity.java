package com.hgz.test.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hgz.test.recyclerview.adapter.MyAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Button button = (Button) findViewById(R.id.button);

        //RecyclerView的展示样式
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 3);

        //设置RecyclerView的展示样式
        recyclerView.setLayoutManager(linearLayoutManager);

        //gridview的形式展示,可以通过setSpanSizeLookup 来自定义每个item占的列数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3 - position % 3;
            }
        });
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //if和elseif是有先后顺序的,先判断范围小的,然后再判断范围大的,
        //因为GridLayoutManager 是继承 LinearLayoutManager ,所以他本质上也是LinearLayoutManager,
        //所以不能先判断是否是LinearLayoutManager (LinearLayoutManager范围大)
        if (layoutManager instanceof GridLayoutManager){
            recyclerView.setLayoutManager(linearLayoutManager);
        }else if(layoutManager instanceof LinearLayoutManager){
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}
