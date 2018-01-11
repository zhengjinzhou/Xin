package com.zhou.xin.ui.activity.love;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GuildInfoActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;

    public static Intent newIntent(Context context,String str){
        Intent intent = new Intent(context,GuildInfoActivity.class);
        intent.putExtra("head",str);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guild_info;
    }

    @Override
    protected void init() {
        if (getIntent() != null){
            String head = getIntent().getStringExtra("head");
            tv_head.setText(head);
        }
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
