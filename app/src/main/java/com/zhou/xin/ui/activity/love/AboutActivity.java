package com.zhou.xin.ui.activity.love;

import android.view.View;
import android.widget.TextView;

import com.zhou.xin.BuildConfig;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.tv_code)
    TextView tv_code;

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        tv_head.setText("关于应用");
        //获取当前版本号
        tv_code.setText("当前版本V：" + BuildConfig.VERSION_NAME);
    }


    @OnClick({R.id.back, R.id.rl_app, R.id.rl_update})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_app:
                startToActivity(AboutInfoActivity.class);
                break;
            case R.id.rl_update:
                update();
                break;
        }
    }

    /**
     * 软件更新
     */
    private void update() {
        //获取服务器json，解析，对比，下载、安装
        ToastUtil.show(getApplicationContext(), "当前为最新版本");
    }
}
