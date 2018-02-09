package com.zhou.xin.ui.activity.love.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.tv_show) TextView tv_show;
    @BindView(R.id.tv_message) TextView tv_message;
    boolean isShow = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void init() {
        toolbar.setLogo(R.drawable.bar_back_white);
        collapsing_toolbar.setTitle("标题");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    @OnClick({R.id.tv_show}) void onClick(View view){
        switch (view.getId()){
            case R.id.tv_show:
                if (!isShow){
                    tv_message.setMaxLines(999);
                    isShow = true;
                    tv_show.setText("收起");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_up);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                }else {
                    tv_message.setMaxLines(3);
                    isShow = false;
                    tv_show.setText("展开");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_down);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                }
                break;
        }
    }
}
