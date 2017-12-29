package com.zhou.xin.ui.activity.huanxin;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.App;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.db.DemoDBManager;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_user) EditText et_user;
    @BindView(R.id.et_password) EditText et_password;
    private String user;
    private String password;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.bt_register,R.id.bt_login}) void onClick(View view){
        switch (view.getId()){
            case R.id.bt_register:
                user = et_user.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                register(user,password);
                break;
            case R.id.bt_login:
                user = et_user.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                login(user,password);
                break;

        }
    }


    //注册
    private void register(final String username, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
        // call method in SDK
                try {
                    EMClient.getInstance().createAccount(username, pwd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DemoHelper.getInstance().setCurrentUserName(username);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode=e.getErrorCode();
                            if(errorCode== EMError.NETWORK_ERROR){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    //登录
    private void login(final String username, final String pwd) {
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(username);
        final long start = System.currentTimeMillis();
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
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
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onProgress(int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
