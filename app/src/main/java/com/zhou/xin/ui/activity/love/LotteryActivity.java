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

import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.WinnersBean;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;
import com.zhou.xin.widget.VerticalTextview;
import com.zhou.xin.widget.lottery.listener.RotateListener;
import com.zhou.xin.widget.lottery.view.WheelSurfView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LotteryActivity extends BaseActivity {

    private static final String TAG = "LotteryActivity";
    @BindView(R.id.wheelSurfView1) WheelSurfView wheelSurfView1;
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.textView) VerticalTextview textView;

    private List<String> titleList = new ArrayList<>();
    private List<String> imgUrl = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_lottery;
    }

    @Override
    protected void init() {
        tv_head.setText("抽奖活动");
        initwinnerList();//获取个人中奖记录
        initLotteryHttp();//获取中奖列表
        //获取第二个视图
        //添加滚动监听
        initLottery();
        initTextView();
    }

    /**
     * 获取中奖列表
     */
    private void initLotteryHttp() {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "17";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        final String _s = Md5Util.encoder(joint);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "获取中奖列表: "+e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "获取中奖列表: "+string);
                Gson gson = new Gson();
                WinnersBean winnersListBean = gson.fromJson(string, WinnersBean.class);
                WinnersBean.LuckdrawBean luckdraw = winnersListBean.getLuckdraw();
                List<WinnersBean.LuckdrawBean.PrizeSetBean> prizeSet = luckdraw.getPrizeSet();
                for (int i=0;i<prizeSet.size();i++){
                    imgUrl.add(prizeSet.get(i).getMini_url());

                }
            }
        });
    }

    private void initTextView() {

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
                ToastUtil.show(getApplicationContext(),"恭喜，您中了" + des);
                initmember(position);//记录中奖者
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {
                Log.d(TAG, "rotating: "+valueAnimator);
            }

            @Override
            public void rotateBefore(ImageView goImg) {
                int position = new Random().nextInt(7) + 1;
                wheelSurfView1.startRotate(position);
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

    private void initmember(int pos) {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "18";
        String memberId = App.getInstance().getUserInfo().getUid();
        String prizeId = pos+"";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&memberId=" + memberId +"&opt=" + opt +"&prizeId="+ prizeId + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("opt", opt)
                .add("memberId",memberId)
                .add("prizeId",prizeId)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "记录中奖者: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "记录中奖者: "+string);
            }
        });
    }

    /**
     * 获取个人中奖记录
     */
    private void initwinnerList() {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "19";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "获取个人中奖记录: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Log.d(TAG, "获取个人中奖记录: "+string);
            }
        });
    }
}
