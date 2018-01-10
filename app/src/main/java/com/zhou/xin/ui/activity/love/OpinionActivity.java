package com.zhou.xin.ui.activity.love;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class OpinionActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_opinion) EditText et_opinion;

    @Override
    protected int getLayout() {
        return R.layout.activity_opinion;
    }

    @Override
    protected void init() {
            tv_head.setText("意见反馈");
    }

    @OnClick({R.id.back,R.id.bt_submit,R.id.rl_add}) void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit://提交反馈
                submit();
                break;
            case R.id.rl_add:

                break;
        }
    }

    //提交信息
    private void submit() {
        String opinion = et_opinion.getText().toString().trim();
        if (TextUtils.isEmpty(opinion)){
            ToastUtil.show(getApplicationContext(),"意见不能为空");
            return;
        }

    }
}
