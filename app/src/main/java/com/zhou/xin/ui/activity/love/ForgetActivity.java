package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CountDownTimerUtils;
import com.zhou.xin.utils.PhoneUtil;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_mobile) EditText et_mobile;
    @BindView(R.id.et_code) EditText et_code;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.et_confirm) EditText et_confirm;
    @BindView(R.id.tv_code) TextView tv_code;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected void init() {
        tv_head.setText("找回密码");
    }

    @OnClick({R.id.back, R.id.bt_prove, R.id.tv_code})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                //获取验证码
                getCode();
                break;
            case R.id.bt_prove:
                prove();//验证
                break;
        }
    }

    /**
     * 获取手机验证码
     */
    private void getCode() {
        String mobile = et_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show(getApplicationContext(), "手机号码不能为空");
            return;
        }
        boolean phoneNumber = PhoneUtil.isPhoneNumber(mobile);
        if (!phoneNumber) {
            ToastUtil.show(getApplicationContext(), "情输入有效手机号码");
            return;
        }
        CountDownTimerUtils timerUtils = new CountDownTimerUtils(tv_code, 60000, 1000);
        timerUtils.start();

        //余下待续
    }

    /**
     * 更改密码
     */
    private void prove() {
        String code = et_code.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm = et_confirm.getText().toString().trim();
        String mobile = et_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show(getApplicationContext(), "手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.show(getApplicationContext(), "验证码不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "新密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(confirm)) {
            ToastUtil.show(getApplicationContext(), "再次输入新密码不能为空");
            return;
        }

        if (!password.equals(confirm)){
            ToastUtil.show(getApplicationContext(), "两次输入密码不一致，请重新输入");
            return;
        }

        //余下待续  成功之后 SpUtil.putString(getApplicationContext(), Constant.PASSWORD,password); 修改本机处密码

    }
}
