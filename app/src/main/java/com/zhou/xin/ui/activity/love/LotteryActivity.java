package com.zhou.xin.ui.activity.love;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.ToastUtil;
import com.zhou.xin.widget.VerticalTextview;
import com.zhou.xin.widget.lottery.listener.RotateListener;
import com.zhou.xin.widget.lottery.view.WheelSurfView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class LotteryActivity extends BaseActivity {

    private static final String TAG = "LotteryActivity";
    @BindView(R.id.wheelSurfView1) WheelSurfView wheelSurfView1;
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.textView) VerticalTextview textView;

    private List<String> titleList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_lottery;
    }

    @Override
    protected void init() {
        tv_head.setText("抽奖活动");
        //获取第二个视图
        //添加滚动监听
        initLottery();
        initTextView();
    }

    private void initTextView() {
        titleList.add("恭喜！！张三中了三等奖");
        titleList.add("恭喜！！李四中了三等奖");
        titleList.add("恭喜！！周哥哥中了三等奖");
        titleList.add("恭喜！！周帅哥中了三等奖");
        titleList.add("恭喜！！周爷爷中了三等奖");
        titleList.add("恭喜！！周中了三等奖");
        titleList.add("你踏着七彩祥云离去");
        titleList.add("我被留在这里");

        textView.setTextList(titleList);
        textView.setText(16, 5, Color.RED);//设置属性
        textView.setTextStillTime(3000);//设置停留时长间隔
        textView.setAnimTime(300);//设置进入和退出的时间间隔
        textView.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.show(getApplicationContext(),"点击了 : " + titleList.get(position));
            }
        });
    }

    private void initLottery() {
        wheelSurfView1.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position, String des) {
                ToastUtil.show(getApplicationContext(),"结束了 位置：" + position + "   描述：" + des);
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {
                Log.d(TAG, "rotating: "+valueAnimator);
            }

            @Override
            public void rotateBefore(ImageView goImg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LotteryActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("确定要花费100积分抽奖？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //模拟位置
                        int position = new Random().nextInt(7) + 1;
                        wheelSurfView1.startRotate(position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    @OnClick({R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        textView.stopAutoScroll();
    }
}
