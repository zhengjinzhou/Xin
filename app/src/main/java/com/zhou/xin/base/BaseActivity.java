package com.zhou.xin.base;


import android.annotation.SuppressLint;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by zhou on 2017/12/19.
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends EaseBaseActivity {

    public abstract int getLayout();
    public abstract void initV();


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
        //MobclickAgent.onPause(this);
    }
}
