package com.zhou.xin.ui.activity.love.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "ActivityActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;
    @BindView(R.id.iv_add) ImageView iv_add;
    private static final int CHOOSE_PHOTO = 1;
    private ImageView iv_img;
    String imagePath = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_activity;
    }

    @Override
    protected void init() {
        getInfo();
        tv_head.setText("线下活动");
        //iv_add.setVisibility(View.VISIBLE);
        //iv_add.setImageResource(R.drawable.addto);
        initRecycle();
        refresh.setOnRefreshListener(this);
    }

    /**
     * 接口获取信息
     */
    private void getInfo() {
        dialog.show();
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "22";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        Log.d(TAG, "getInfo: "+joint);
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("_t", _t)
                .add("_s", _s)
                .add("token", token)
                .add("opt", opt)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.LOGIN_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: "+string);
                dialog.dismiss();
            }
        });
    }

    private void initRecycle() {
        List<String> data = new ArrayList<>();
        int num = new Random().nextInt(10) * 1;
        for (int i=0;i<num;i++){
            data.add(i+"");
        }
        CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.recycle_activity, data) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                holder.setOnClickListener(R.id.ll_activity, v -> {
                    startToActivity(ActivityInfoActivity.class);//要传参
                });
            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.back,R.id.iv_add}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_add:
                startToActivity(SendActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> refresh.setRefreshing(false),2000);
    }

}
