package com.zhou.xin.ui.activity.huanxin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;

public class ExitGroupDialog extends BaseActivity {

    @BindView(R.id.tv_text) TextView text;
    @BindView(R.id.btn_exit) Button exitBtn;

    @Override
    public int getLayout() {
        return R.layout.activity_exit_group_dialog;
    }

    @Override
    public void init() {
        text.setText(R.string.exit_group_hint);
        String toast = getIntent().getStringExtra("deleteToast");
        if(toast != null)
            text.setText(toast);
        exitBtn.setText(R.string.exit_group);
    }
    public void logout(View view){
        setResult(RESULT_OK);
        finish();

    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

}
