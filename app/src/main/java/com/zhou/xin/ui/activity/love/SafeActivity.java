package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeActivity extends BaseActivity {

    @BindView(R.id.tv_head)
    TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe;
    }

    @Override
    protected void init() {
        tv_head.setText("安全管理");
    }

    @OnClick({R.id.back}) void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
