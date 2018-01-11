package com.zhou.xin.ui.activity.love;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;

    @Override
    protected int getLayout() {
        return R.layout.activity_success;
    }

    @Override
    protected void init() {
        tv_head.setText("成功案例");
        initRecycle();
        refresh.setOnRefreshListener(this);
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void initRecycle() {
        List<String> data = new ArrayList<>();
        int num = new Random().nextInt(10) * 1;
        for (int i=0;i<num;i++){
            data.add(i+"");
        }

        CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.recycle_success, data) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {

            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        },2000);
    }
}
