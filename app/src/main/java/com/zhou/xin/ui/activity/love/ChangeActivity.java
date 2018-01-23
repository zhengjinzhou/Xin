package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.UserInfo;
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

/**
 * 修改密码
 */
public class ChangeActivity extends BaseActivity {

    private static final String ATG = "ChangeActivity";

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_old) EditText et_old;
    @BindView(R.id.et_new) EditText et_new;
    @BindView(R.id.et_confirm) EditText et_confirm;

    @Override
    protected int getLayout() {
        return R.layout.activity_change;
    }

    @Override
    protected void init() {
        tv_head.setText("修改密码");
    }

    @OnClick({R.id.back,R.id.bt_change}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_change:
                //修改密码
                changePassword();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void changePassword() {
        String etOld = et_old.getText().toString().trim();
        final String etNew = et_new.getText().toString().trim();
        String etConfirm = et_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(etOld)){
            ToastUtil.show(getApplicationContext(),"原密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(etNew)){
            ToastUtil.show(getApplicationContext(),"新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(etConfirm)){
            ToastUtil.show(getApplicationContext(),"确认密码不能为空");
            return;
        }
        if (!etNew.equals(etConfirm)){
            ToastUtil.show(getApplicationContext(),"新密码输入不一致");
            return;
        }

        String psd = SpUtil.getString(getApplicationContext(), Constant.PASSWORD, "");
        if (psd == null){
            ToastUtil.show(getApplicationContext(),"原密码不存在");
            return;
        }

        if (!etOld.equals(psd)){
            ToastUtil.show(getApplicationContext(),"原密码不正确");
            return;
        }

        String token = App.getInstance().getUserInfo().getToken();
        String opt = "15";
        String _t = CurrentTimeUtil.nowTime();
        String oldPwd = DES3Util.encrypt3DES(psd, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String newPwd = DES3Util.encrypt3DES(etNew, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String joint = "_t=" + _t + "&newPwd="+newPwd+ "&oldPwd=" + oldPwd + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        System.out.println("拼接后_t的数据--------" + joint);

        dialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("token",token)
                .add("opt",opt)
                .add("oldPwd",oldPwd)
                .add("newPwd",newPwd)
                .add("_t",_t)
                .add("_s",_s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(ATG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(getApplicationContext(),"密码修改成功");
                            SpUtil.putString(getApplicationContext(), Constant.PASSWORD,etNew);
                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }
}
