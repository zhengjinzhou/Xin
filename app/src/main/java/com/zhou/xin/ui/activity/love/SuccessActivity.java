package com.zhou.xin.ui.activity.love;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.MultiItemCommonAdapter;
import com.zhou.xin.adapter.base.MultiItemTypeSupport;
import com.zhou.xin.adapter.base.SpacesItemDecoration;
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
    private MultiItemCommonAdapter adapter;

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
        //一位数为照片文字  两位数为视频  三位数文字
        List<String> data = new ArrayList<>();
        data.add("11");data.add("11");data.add("111");
        data.add("111");data.add("1");data.add("1");
        data.add("11");data.add("111");data.add("11");
        data.add("11");data.add("1");data.add("1");
        data.add("1");data.add("1");data.add("1");
        data.add("1");data.add("111");data.add("111");
        adapter = new MultiItemCommonAdapter<String>(this, data, new MultiItemTypeSupport<String>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 1){//照片文字
                    return R.layout.view_issue_imager_text;
                }else if (itemType == 2){
                    return R.layout.view_video;
                }
                return R.layout.view_text;
            }

            @Override
            public int getItemViewType(int position, String s) {
                if (s.length()==3){
                    return 3;
                }
                if (s.length()==1){
                    return 1;
                }
                if (s.length() ==2){
                    return 2;
                }
                return 3;
            }
        }) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                int itemCount = getItemCount();
                if (itemCount==1){
                    //textImagerManger(holder,bean,position);
                }else if (itemCount==2){
                    //textVedioManger(holder,bean,position);
                }else {
                    //textManger(holder,bean,position);
                }
                //举报
                holder.setOnClickListener(R.id.iv_report, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startToActivity(ReportActivity.class);/***这里到时候要传参数过去*/
                        Report();
                    }
                });
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
    }

    /**
     * 举报，有参数要传，待续
     */
    private void Report() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_report, null);
        dialog.setView(inflate,0,0,0,0);
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(ReportActivity.class);//举报，有参数要传，待续
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
