package com.zhou.xin.ui.activity.love;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.ReportBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Report2Activity extends BaseActivity {

    private static final String TAG = "Report2Activity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_report) TextView tv_report;

    public static Intent newintent(Context context,String str){
        Intent intent = new Intent(context,Report2Activity.class);
        intent.putExtra("acTypes",str);
        return intent;
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_report;
    }

    @Override
    protected void init() {
        tv_head.setText("举报用户");
        tv_report.setVisibility(View.GONE);

        String acTypes = getIntent().getStringExtra("acTypes");
        if (acTypes != null){
            Gson gson = new Gson();
            Log.d(TAG, "init: "+acTypes);

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
