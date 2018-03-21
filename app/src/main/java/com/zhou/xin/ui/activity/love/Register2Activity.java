package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.bean.ZhuCeBean;
import com.zhou.xin.ui.activity.huanxin.LoginActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DES3Util;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register2Activity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm)
    EditText et_confirm;
    @BindView(R.id.et_inviteCode)
    EditText et_inviteCode;
    @BindView(R.id.tv_head)
    TextView tv_head;

    private static final String TAG = "Register2Activity";
    private String mobile;//手机号码
    private String code;//验证码

    public static Intent newIntent(Context context, String mobile, String code) {
        Intent intent = new Intent(context, Register2Activity.class);
        intent.putExtra(Constant.MOBILE, mobile);
        intent.putExtra("code", code);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register2;
    }

    @Override
    protected void init() {
        tv_head.setText("注册");
        Intent intent = getIntent();
        mobile = intent.getStringExtra(Constant.MOBILE);
        code = intent.getStringExtra("code");
        Log.d(TAG, "init: " + mobile + "," + code);
    }

    @OnClick({R.id.back, R.id.bt_register})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_register:
                toRegister();
                break;
        }
    }

    /**
     * 注册
     */
    private void toRegister() {
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        String confirm = et_confirm.getText().toString().trim();
        String inviteCode = et_inviteCode.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(getApplicationContext(), "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(confirm)) {
            ToastUtil.show(getApplicationContext(), "确认密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "密码不能小于6位");
            return;
        }

        if (TextUtils.isEmpty(inviteCode)) {
            ToastUtil.show(getApplicationContext(), "邀请码不能为空");
            return;
        }

        if (!password.equals(confirm)) {
            ToastUtil.show(getApplicationContext(), "密码与确认密码不一致");
            return;
        }

        //注册
        String opt = "1";
        String _t = CurrentTimeUtil.nowTime();
        String pwd = DES3Util.encrypt3DES(password, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String joint = "_t=" + _t + "&code=" + code +"&inviteCode="+inviteCode+ "&mobile=" + mobile + "&opt=" + opt + "&password=" + pwd + "&username=" + username  + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        Log.d(TAG, "getCode: " + joint);
        dialog.show();
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("opt", opt)
                .add("mobile", mobile)
                .add("code", code)
                .add("username", username)
                .add("password", pwd)
                .add("inviteCode", inviteCode)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.URL_REGISTER)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "注册onResponse: " + string);
                Gson gson = new Gson();
                ZhuCeBean zhuCeBean = gson.fromJson(string, ZhuCeBean.class);
                if (zhuCeBean.getError().equals("-1")){
                    /**
                     * 环信注册
                     * 环信的密码固定位电话号码的拼接后md5加密
                     */
                    xinRegister(username, username);
                    //保存密码，用在修改密码处于原密码进行对比
                    SpUtil.putString(getApplicationContext(),Constant.PASSWORD,password);
                }else {
                    dialog.dismiss();
                    runOnUiThread(() -> ToastUtil.show(getApplicationContext(),zhuCeBean.getMsg()));
                }
            }
        });
    }

    //注册
    private void xinRegister(final String username, final String pwd) {
        new Thread(() -> {
            // call method in SDK
            try {
                String psswordd = Md5Util.encoder(pwd+Constant.APP_ENCRYPTION_KEY);
                EMClient.getInstance().createAccount(username, psswordd);
                runOnUiThread(() -> {
                    DemoHelper.getInstance().setCurrentUserName(username);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                    startToActivity(AppActivity.class);//本应用于环信都注册成功之后跳转到登录界面
                    dialog.dismiss();
                });
            } catch (final HyphenateException e) {
                runOnUiThread(() -> {
                    int errorCode = e.getErrorCode();
                    if (errorCode == EMError.NETWORK_ERROR) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
