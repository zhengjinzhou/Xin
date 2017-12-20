package com.zhou.xin.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

public class ContextMenuActivity extends BaseActivity {

    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;
    public static final int RESULT_CODE_FORWARD = 3;
    public static final int RESULT_CODE_RECALL = 4;

    @Override
    public int getLayout() {
        return R.layout.activity_context_menu;
    }

    @Override
    public void initV() {

    }

}
