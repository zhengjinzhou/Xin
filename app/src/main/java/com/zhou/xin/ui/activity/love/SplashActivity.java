package com.zhou.xin.ui.activity.love;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.ui.activity.huanxin.LoginActivity;
import com.zhou.xin.utils.SpUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (SpUtil.getString(getApplicationContext(), Constant.ISLOGIN,"").equals("ISLOGIN")){
            startToActivity(LoginActivity.class);
            finish();
            return;
        }

        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToActivity(AppActivity.class);
                finish();
            }
        },1500);
    }
}
