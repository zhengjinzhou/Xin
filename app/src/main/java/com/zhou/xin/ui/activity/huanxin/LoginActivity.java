package com.zhou.xin.ui.activity.huanxin;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.App;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.db.DemoDBManager;
import com.zhou.xin.ui.activity.love.ForgetActivity;
import com.zhou.xin.ui.activity.love.RegisterActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DES3Util;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.circle) CircleImageView circle;
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_forget) TextView tv_forget;
    private String TAG = "LoginActivity";
    private String username;
    private String password;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        tv_head.setText("登陆");
        etUsername.setText("2014414");
        Log.d(TAG, "init: "+Md5Util.encoder("2014414"+Constant.APP_ENCRYPTION_KEY));
        etPassword.setText("123456");
    }

    @OnClick({R.id.bt_login, R.id.back, R.id.tv_forget}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();//登录
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_forget:
                startToActivity(ForgetActivity.class);//忘记密码
                break;
        }
    }

    private void login() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(getApplicationContext(), "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }

        dialog.show();

        String pwd = DES3Util.encrypt3DES(password, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String opt = "2";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&actNumber=" + username + "&opt=" + opt + "&pwd=" + pwd + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);

        System.out.println("拼接后_t的数据--------" + joint);

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("actNumber", username)
                .add("pwd", pwd)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, final IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(getApplicationContext(), e.getMessage());
                }
            });
            Log.d(TAG, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();
            Log.d(TAG, "onResponse: " + string);
            getResult(string);
        }
    };

    private void getResult(String data) {
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        String token = userInfo.getToken();
        String uid = userInfo.getUid();

        if (userInfo.getError().equals("-3")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    ToastUtil.show(getApplicationContext(), "用户名或密码错误");
                }
            });
            return;
        }
        Log.d(TAG, "getResult: " + token);
        Log.d(TAG, "getResult: " + uid);

        String opt = "5";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + "&uid=" + uid + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("uid", uid)
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call2 = okHttpClient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d(TAG, "---------onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                Gson gson1 = new Gson();
                PersonalBean personalBean = gson1.fromJson(res.body().string(), PersonalBean.class);
                App.getInstence().setPersonalBean(personalBean);
            }
        });

        App.getInstence().setUserInfo(userInfo);
        if (userInfo.getError().equals("-1")) {
            //进行环信登录
            XinLogin(username,username);
            SpUtil.putString(getApplicationContext(), Constant.PASSWORD,password);
        } else if (userInfo.getError().equals("-2")) {
            startToActivity(RegisterActivity.class);
        }
    }
    /**
     * 进行环信登录
     * @param username
     * @param pwd
     */
    private void XinLogin(final String username, final String pwd) {
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        String pssword = Md5Util.encoder(pwd+Constant.APP_ENCRYPTION_KEY);
        Log.d(TAG, "XinLogin: "+username+pssword);
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(username);
        final long start = System.currentTimeMillis();
        EMClient.getInstance().login(username, pssword, new EMCallBack() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                Log.d(TAG, "login: onSuccess");
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        App.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                startToActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onProgress(int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
