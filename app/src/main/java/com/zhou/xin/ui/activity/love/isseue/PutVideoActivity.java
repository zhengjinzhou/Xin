package com.zhou.xin.ui.activity.love.isseue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.ui.activity.love.isseue.VideoPlayerActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PutVideoActivity extends BaseActivity {

    @BindView(R.id.content) EditText content;
    @BindView(R.id.video) ImageView video;
    @BindView(R.id.url) TextView url;

    @Override
    protected int getLayout() {
        return R.layout.activity_put_video;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                upVideo();
                break;
            case R.id.video:
               //startActivity(new Intent(this, VideoPlayerActivity.class).putExtra("path", videoUri));
                break;
        }
    }

    private void upVideo() {

    }
}
