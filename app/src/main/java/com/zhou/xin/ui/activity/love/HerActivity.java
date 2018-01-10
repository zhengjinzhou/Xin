package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HerActivity extends BaseActivity {

    @BindView(R.id.iv_man) ImageView ivMan;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.tv_constellation) TextView tvConstellation;
    @BindView(R.id.tv_major) TextView tvMajor;
    @BindView(R.id.recycle) RecyclerView recycle;

    @Override
    protected int getLayout() {
        return R.layout.activity_her;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.iv_all_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_all_back:
                finish();
                break;
        }
    }
}
