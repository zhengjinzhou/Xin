package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportInfoActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;

    public static Intent newIntent(Context context, String str){
        Intent intent = new Intent(context,ReportInfoActivity.class);
        intent.putExtra("typeId",str);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_report_info;
    }

    @Override
    protected void init() {
        tv_head.setText("举报用户");
    }

    @OnClick({R.id.back,R.id.bt_submit,R.id.rl_add}) void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit://提交反馈
                //submit();
                break;
            case R.id.rl_add:
                //getPhone();
                break;
        }
    }
}
