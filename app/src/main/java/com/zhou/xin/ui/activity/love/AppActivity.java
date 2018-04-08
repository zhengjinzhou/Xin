package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.whinc.widget.fontview.FontUtils;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.ui.activity.huanxin.LoginActivity;
import com.zhou.xin.utils.Md5Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 改界面没有什么功能，就是选择注册还是登陆
 */
public class AppActivity extends BaseActivity {

    @BindView(R.id.tv_love) TextView tv_love;

    @Override
    protected int getLayout() {
        return R.layout.activity_app;
    }

    @Override
    protected void init() {

        Log.d("huanxinmima", "init: " + Md5Util.encoder("13631789689" + Constant.APP_ENCRYPTION_KEY));
        Log.d("huanxinmima", "init: " + Md5Util.encoder("13631789689" + Constant.APP_ENCRYPTION_KEY));
        Log.d("huanxinmima", "init: " + Md5Util.encoder("13631784759" + Constant.APP_ENCRYPTION_KEY));
        Log.d("huanxinmima", "init: " + Md5Util.encoder("13631782249" + Constant.APP_ENCRYPTION_KEY));
        Log.d("huanxinmima", "init: " + Md5Util.encoder("13631784659" + Constant.APP_ENCRYPTION_KEY));

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        FontUtils.getInstance().replaceFontFromAsset(tv_love, "fonts/ArialRoundedMTStd-Bold.otf");
    }

    @OnClick({R.id.bt_login, R.id.bt_register})
    void onCliuck(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startToActivity(LoginActivity.class);
                break;
            case R.id.bt_register:
                startToActivity(RegisterActivity.class);
                break;
        }
    }
}
