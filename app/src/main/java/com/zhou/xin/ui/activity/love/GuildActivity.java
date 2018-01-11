package com.zhou.xin.ui.activity.love;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuildActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.recycleView) RecyclerView recyclerView;


    @Override
    protected int getLayout() {
        return R.layout.activity_guild;
    }

    @Override
    protected void init() {
        tv_head.setText("帮助中心");
        initRecycle();
    }

    /**
     * recyclerView
     */
    private void initRecycle() {
        final List<String> data = new ArrayList<>();
        for (int i=0;i<24;i++){
            data.add(i+"");
        }
        CommonAdapter adapter = new CommonAdapter(this, R.layout.recycle_guild, data) {
            @Override
            public void convert(ViewHolder holder, Object o, final int position) {
                holder.setText(R.id.tv_des,data.get(position));
                holder.setOnClickListener(R.id.rl_guild, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(GuildInfoActivity.newIntent(getApplicationContext(),data.get(position)));
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
