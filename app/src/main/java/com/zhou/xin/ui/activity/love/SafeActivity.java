package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_cache) TextView tv_cache;

    @Override
    protected int getLayout() {
        return R.layout.activity_safe;
    }

    @Override
    protected void init(){
        tv_head.setText("安全管理");
        //获取应用缓存大小
        getCach();
    }

    /**
     * 缓存应用缓存大小
     */
    private void getCach() {
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tv_cache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back,R.id.rl_clean,R.id.rl_change}) void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_clean:
                dialog.show();
                DataCleanManager.clearAllCache(getApplicationContext());
                dialog.dismiss();
                getCach();
                break;
            case R.id.rl_change:
                startToActivity(ChangeActivity.class);//修改用户密码
                break;
        }
    }
}
