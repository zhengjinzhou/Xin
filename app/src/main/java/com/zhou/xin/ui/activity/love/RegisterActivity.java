package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CountDownTimerUtils;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.PhoneUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_mobile) EditText et_mobile;
    @BindView(R.id.et_code) EditText et_code;
    @BindView(R.id.tv_code) TextView tv_code;
    @BindView(R.id.clear) ImageView clear;


    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        tv_head.setText("注册");
        clear.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.back, R.id.tv_code, R.id.bt_next,R.id.clear})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                getCode();
                break;
            case R.id.bt_next:
                toNext();
                break;
            case R.id.clear:
                et_mobile.setText("");
                break;
        }
    }

    @OnTextChanged(value = R.id.et_mobile, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void changedUserName() {
        int lenght = et_mobile.getText().toString().length();
        if (lenght > 0) {
            clear.setVisibility(View.VISIBLE);
        } else {
            clear.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取验证码
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

        //联网获取验证码
        String opt = "0";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&mobile=" + mobile + "&opt=" + opt + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        Log.d(TAG, "getCode: " + joint + "_s" + _s);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("opt", opt)
                .add("mobile", mobile)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.URL_REGISTER).build();
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

    /**
     * 下一步，进入另外一个注册界面
     */
    private void toNext() {
        String mobile = et_mobile.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show(getApplicationContext(), "手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.show(getApplicationContext(), "验证码不能为空");
            return;
        }
        startActivity(Register2Activity.newIntent(this, mobile, code));
    }
}

