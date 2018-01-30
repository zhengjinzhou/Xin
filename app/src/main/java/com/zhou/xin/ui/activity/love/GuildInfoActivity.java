package com.zhou.xin.ui.activity.love;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.GuildBean;

import butterknife.BindView;
import butterknife.OnClick;

public class GuildInfoActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_desc) TextView tv_desc;


    public static Intent newIntent(Context context,String title,String desc){
        Intent intent = new Intent(context,GuildInfoActivity.class);
        intent.putExtra("guild_title", title);
        intent.putExtra("guild_desc", desc);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guild_info;
    }

    @Override
    protected void init() {
        if (getIntent() != null){
            String head = getIntent().getStringExtra("guild_title");
            String desc = getIntent().getStringExtra("guild_desc");
            tv_head.setText(head);
            tv_desc.setText(desc);
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
