package com.zhou.xin.ui.activity.love.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityInfoActivity extends BaseActivity {

    private static final String TAG = "ActivityInfoActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.tv_show)
    TextView tv_show;
    @BindView(R.id.tv_message)
    TextView tv_message;
    boolean isShow = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void init() {
        getInfo();
        toolbar.setLogo(R.drawable.bar_back_white);
        collapsing_toolbar.setTitle("标题");
    }

    /**
     *
     */
    private void getInfo() {
        dialog.show();
        String token = App.getInstance().getUserInfo().getToken();
        String mobile = App.getInstance().getUserInfo().getAccountNumber();
        String opt = "23";
        String activityId = "1";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&activityId=" + activityId + "&mobile=" + mobile + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        Log.d(TAG, "getInfo: " + joint);
        String _s = Md5Util.encoder(joint);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("_t", _t)
                .add("_s", _s)
                .add("token", token)
                .add("opt", opt)
                .add("activityId", activityId)
                .add("mobile", mobile)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.LOGIN_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @OnClick({R.id.tv_show})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show:
                if (!isShow) {
                    tv_message.setMaxLines(999);
                    isShow = true;
                    tv_show.setText("收起");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_up);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                } else {
                    tv_message.setMaxLines(3);
                    isShow = false;
                    tv_show.setText("展开");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_down);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                }
                break;
        }
    }
}
