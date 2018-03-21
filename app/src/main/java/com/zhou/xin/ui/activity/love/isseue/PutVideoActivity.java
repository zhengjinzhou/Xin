package com.zhou.xin.ui.activity.love.isseue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.ui.activity.love.isseue.VideoPlayerActivity;
import com.zhou.xin.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PutVideoActivity extends BaseActivity {

    private static final String TAG = "PutVideoActivity";
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.video)
    ImageView video;
    @BindView(R.id.url)
    TextView url;
    private String videoUri;
    private String videoScreenshot;

    @Override
    protected int getLayout() {
        return R.layout.activity_put_video;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        videoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
        Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
        video.setImageBitmap(bitmap);
        url.setHint("您视频地址为:" + videoUri);

        Log.d(TAG, "upVideo: " + videoUri);
    }

    @OnClick({R.id.back, R.id.bt_submit, R.id.video})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                upVideo();
                break;
            case R.id.video:
                startActivity(new Intent(this, VideoPlayerActivity.class).putExtra("path", videoUri));
                break;
        }
    }

    /**
     * 上传视频
     */
    private void upVideo() {
        String videoMiniFile = "/storage/emulated/0/DCIM/Camera/IMG_20171220_021957.jpg";//用来测试的视频缩略图
        Log.d(TAG, "视频缩略图视频缩略图视频缩略图视频缩略图?: "+videoScreenshot);
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builider = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builider.addFormDataPart("token", App.getInstance().getUserInfo().getToken());
        if (!TextUtils.isEmpty(content.getText().toString()))
            builider.addFormDataPart("content", content.getText().toString());
        builider.addFormDataPart("videoFile", videoUri, RequestBody.create(MediaType.parse("file"), new File(videoUri)));
        builider.addFormDataPart("videoMiniFile", videoScreenshot, RequestBody.create(MediaType.parse("image/*"), new File(videoScreenshot)));
        MultipartBody body = builider.build();
        dialog.show();
        Request request = new Request.Builder()
                .url(Constant.POST_LIST)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();

                String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                Gson gson = new Gson();
                final UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")){
                    runOnUiThread(() -> {
                        ToastUtil.show(getApplicationContext(),userInfo.getMsg());
                        finish();
                    });
                }
            }
        });
    }
}
