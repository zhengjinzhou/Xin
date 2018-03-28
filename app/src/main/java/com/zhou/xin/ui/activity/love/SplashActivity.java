package com.zhou.xin.ui.activity.love;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhou.xin.BuildConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.bean.VerBean;
import com.zhou.xin.db.DemoDBManager;
import com.zhou.xin.download.UpdateManager;
import com.zhou.xin.ui.activity.huanxin.LoginActivity;
import com.zhou.xin.ui.activity.huanxin.MainActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DES3Util;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private String username;
    private String password;

    android.os.Handler handler = new Handler();
    int versionCode = BuildConfig.VERSION_CODE;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {

        if (versionCode > 0){
            new UpdateManager(this).checkUpdate(false);
            findViewById(R.id.pro).setVisibility(View.VISIBLE);
            return;
        }
        /**
         * 没有被异常销毁的时候
         */
        if (App.getInstance().getUserInfo() != null && SpUtil.getString(getApplicationContext(), Constant.ISLOGIN, "").equals("ISLOGIN")) {
            startToActivity(MainActivity.class);
            finish();
            return;
        }

        /**
         * 被异常销毁的时候
         */
        if (App.getInstance().getUserInfo() == null && SpUtil.getString(getApplicationContext(), Constant.ISLOGIN, "").equals("ISLOGIN")) {
            handler.postDelayed(() -> login(), 2000);
            return;
        }

        handler.postDelayed(() -> {
            startToActivity(AppActivity.class);
            finish();
        }, 2000);
    }


    /**
     * 登录
     */
    private void login() {
        username = SpUtil.getString(getApplicationContext(), Constant.USERNAME, "");
        password = SpUtil.getString(getApplicationContext(), Constant.PASSWORD, "");
        String pwd = DES3Util.encrypt3DES(password, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String opt = "2";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&actNumber=" + username + "&opt=" + opt + "&pwd=" + pwd + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
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
            runOnUiThread(() -> ToastUtil.show(getApplicationContext(), e.getMessage()));
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();
            getResult(string);
        }
    };

    /**
     * opt5为获取个人信息
     */
    private void getResult(String data) {
        Gson gson = new Gson();
        final UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        String token = userInfo.getToken();
        String uid = userInfo.getUid();
        if (userInfo.getError().equals("-3")) {
            runOnUiThread(() -> {
                dialog.dismiss();
                ToastUtil.show(getApplicationContext(), userInfo.getMsg()+"请检查您的手机时间");
                finish();
            });
            return;
        }
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
                String string = res.body().string();
                Log.d(TAG, "获取个人信息onResponse: " + string);
                Gson gson1 = new Gson();
                PersonalBean personalBean = gson1.fromJson(string, PersonalBean.class);
                App.getInstance().setPersonalBean(personalBean);
                //设置头像昵称
                Log.d(TAG, "onResponse: " + personalBean.getMemInfo().getNickname() + "  " + Constant.URL + personalBean.getMemInfo().getPhotoPath());
                SpUtil.putString(getApplicationContext(), Constant.USER_NAME, personalBean.getMemInfo().getNickname());
                SpUtil.putString(getApplicationContext(), Constant.HEAD_IMAGE_URL, Constant.URL + personalBean.getMemInfo().getPhotoPath());
                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(personalBean.getMemInfo().getNickname());
                DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(Constant.URL + personalBean.getMemInfo().getPhotoPath());
            }
        });

        App.getInstance().setUserInfo(userInfo);
        if (userInfo.getError().equals("-1")) {
            //进行环信登录
            XinLogin(username, username);
            /*SpUtil.putString(getApplicationContext(), Constant.PASSWORD, password);
            SpUtil.putString(getApplicationContext(),Constant.ISLOGIN,"ISLOGIN");
            SpUtil.putString(getApplicationContext(),Constant.USERNAME,username);*/
        } else if (userInfo.getError().equals("-2")) {
            startToActivity(RegisterActivity.class);
        }
    }

    /**
     * 进行环信登录
     *
     * @param username
     * @param pwd
     */
    private void XinLogin(final String username, final String pwd) {
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        String pssword = Md5Util.encoder(pwd + Constant.APP_ENCRYPTION_KEY);
        Log.d(TAG, "XinLogin: " + username + "  " + pssword);
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
                Log.d(TAG, "login: onProgress" + s);
            }

            @Override
            public void onProgress(int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                            Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "这位爷，在下载呢，待会再退出吧", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
