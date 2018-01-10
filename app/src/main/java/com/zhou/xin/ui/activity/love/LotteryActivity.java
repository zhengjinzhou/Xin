package com.zhou.xin.ui.activity.love;

import android.view.View;
import android.widget.TextView;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class LotteryActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.activity_lottery;
    }

    @Override
    protected void init() {
        tv_head.setText("抽奖活动");
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}