package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CountDownTimerUtils;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.PhoneUtil;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForgetActivity extends BaseActivity {

    private static final String TAG = "ForgetActivity";
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
        String opt = "0";
        String type = "2";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&mobile="+mobile+"&opt=" + opt + "&type=" + type  + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("mobile", mobile)
                .add("type", type)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: "+response);
            }
        });
    }

    /**
     * 更改密码
     */
    private void prove() {
        String code = et_code.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        String confirm = et_confirm.getText().toString().trim();
        final String mobile = et_mobile.getText().toString().trim();
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
        //校验验证码
        String opt= "14";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t +"&code="+code+ "&mobile="+mobile+"&opt=" + opt + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("mobile", mobile)
                .add("code", code)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: "+response.body().string());

                //修改密码
                //toChange(mobile,password);
            }
        });
    }

    /**
     * 修改密码
     * 成功之后 SpUtil.putString(getApplicationContext(), Constant.PASSWORD,password); 修改本机处密码
     * @param mobile
     * @param password
     */
    private void toChange(String mobile, String password) {
        String opt = "3";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&mobile="+mobile+"&opt=" + opt +"&pwd="+password + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("mobile", mobile)
                .add("pwd", password)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
    }
}
