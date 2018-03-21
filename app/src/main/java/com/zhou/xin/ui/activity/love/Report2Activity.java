package com.zhou.xin.ui.activity.love;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.ReportBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Report2Activity extends BaseActivity {

    private static final String TAG = "Report2Activity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_report) TextView tv_report;
    private CommonAdapter adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private List<ReportBean.AccusationCategoryListBean.AcTypesBean> data;

    public static Intent newintent(Context context,String str,String jubao,String mobile){
        Intent intent = new Intent(context,Report2Activity.class);
        intent.putExtra("acTypes",str);
        intent.putExtra("jubao",jubao);
        intent.putExtra("mobile",mobile);
        return intent;
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_report;
    }

    @Override
    protected void init() {
        tv_head.setText("举报用户");
        tv_report.setVisibility(View.GONE);
        data = new ArrayList<>();
        String acTypes = getIntent().getStringExtra("acTypes");
        Gson gson = new Gson();
        data = gson.fromJson(acTypes, new TypeToken<List<ReportBean.AccusationCategoryListBean.AcTypesBean>>() {}.getType());
        initRecycle();
    }

    private void initRecycle() {
        adapter = new CommonAdapter<ReportBean.AccusationCategoryListBean.AcTypesBean>(this, R.layout.recycle_guild, data) {
            @Override
            public void convert(ViewHolder holder, final ReportBean.AccusationCategoryListBean.AcTypesBean s, int position) {
                holder.setText(R.id.tv_des,s.getTypeName());
                holder.setOnClickListener(R.id.rl_guild, v -> startActivity(ReportInfoActivity.newIntent(getApplicationContext(),s.getId()+"",getIntent().getStringExtra("jubao"),getIntent().getStringExtra("mobile"))));
            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
