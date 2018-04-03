package com.zhou.xin.ui.activity.love.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.ActivityBean;
import com.zhou.xin.bean.BaomingBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.ui.activity.love.GuildInfoActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityInfoActivity extends BaseActivity {

    private static final String TAG = "ActivityInfoActivity";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.tv_show) TextView tv_show;
    @BindView(R.id.tv_message) TextView tv_message;
    @BindView(R.id.main_backdrop) ImageView main_backdrop;
    @BindView(R.id.tvHead) TextView tvHead;
    @BindView(R.id.tvPlace) TextView tvPlace;
    @BindView(R.id.tvStart) TextView tvStart;
    @BindView(R.id.tvEnd) TextView tvEnd;
    @BindView(R.id.tv_tip) TextView tv_tip;
    @BindView(R.id.imgRecycler) RecyclerView imgRecycler;

    private int position;
    boolean isShow = false;
    private BaseCommonAdapter adapter;

    public static Intent newIntent(Context context,int pos){
        Intent intent = new Intent(context,ActivityInfoActivity.class);
        intent.putExtra("Position", pos);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void init() {
        getBaoming();
        setInfo();
        toolbar.setLogo(R.drawable.bar_back_white);
        collapsing_toolbar.setTitle("");
        initRecycler();
    }

    /**
     * 所有报名者的显示
     */
    private void initRecycler() {
        ArrayList<BaomingBean.MemberListBean> mDate = new ArrayList<>();
        adapter = new BaseCommonAdapter<BaomingBean.MemberListBean>(this, R.layout.item_activity, mDate) {
            @Override
            public void convert(ViewHolder holder, BaomingBean.MemberListBean s, int position) {
                CircleImageView img = holder.getView(R.id.circle);
                Glide.with(getApplicationContext()).load(Constant.URL+s.getUrl()).into(img);
                holder.setText(R.id.tvName,s.getMemberName());
                holder.setText(R.id.tvAge,s.getAge()+"岁");
            }
        };
        imgRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        imgRecycler.setAdapter(adapter);
    }

    /**
     * 获取报名者
     */
    private void getBaoming() {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "24";
        String activityId = App.getInstance().getActivityBean().getActivityList().get(position).getId()+"";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&activityId=" + activityId + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        Log.d(TAG, "getInfo: " + joint);
        String _s = Md5Util.encoder(joint);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("_t", _t)
                .add("_s", _s)
                .add("token", token)
                .add("opt", opt)
                .add("activityId", activityId)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.LOGIN_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: "+string);
                Gson gson = new Gson();
                BaomingBean baomingBean = gson.fromJson(string, BaomingBean.class);
                List<BaomingBean.MemberListBean> memberList = baomingBean.getMemberList();
                runOnUiThread(() -> {
                    adapter.clear();
                    adapter.add(memberList);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void setInfo() {
        ActivityBean activityBean = App.getInstance().getActivityBean();
        position = getIntent().getIntExtra("Position", 0);
        List<ActivityBean.ActivityListBean> activityList = activityBean.getActivityList();
        Glide.with(this).load(Constant.URL+activityList.get(position).getPhotoUrl()).into(main_backdrop);
        tvHead.setText(activityList.get(position).getActivityName());
        tvPlace.setText(activityList.get(position).getPlace());
        tvStart.setText(activityList.get(position).getStartTime());
        tvEnd.setText("-"+activityList.get(position).getEndTime());
        tv_message.setText(activityList.get(position).getActivityDetail());
        tv_tip.setText(activityList.get(position).getReminder());
    }

    /**
     * 报名参与
     */
    private void baoMing() {
        dialog.show();
        String token = App.getInstance().getUserInfo().getToken();
        String mobile = App.getInstance().getUserInfo().getAccountNumber();
        String opt = "23";
        String activityId = App.getInstance().getActivityBean().getActivityList().get(position).getId()+"";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&activityId=" + activityId + "&mobile=" + mobile + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        Log.d(TAG, "getInfo: " + joint);
        String _s = Md5Util.encoder(joint);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("_t", _t)
                .add("_s", _s)
                .add("token", token)
                .add("opt", opt)
                .add("activityId", activityId)
                .add("mobile", mobile)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.LOGIN_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")){
                    runOnUiThread(() -> ToastUtil.show(getApplicationContext(),userInfo.getMsg()));
                    //成功之后更新头像
                    getBaoming();
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @OnClick({R.id.tv_show,R.id.btSubmit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show:
                if (!isShow) {
                    tv_message.setMaxLines(999);
                    isShow = true;
                    tv_show.setText("收起");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_up);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                } else {
                    tv_message.setMaxLines(3);
                    isShow = false;
                    tv_show.setText("展开");
                    Drawable drawable = getResources().getDrawable(R.drawable.more_unfold_down);// 找到资源图片
                    // 这一步必须要做，否则不会显示。
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                    tv_show.setCompoundDrawables(null, null, drawable, null);// 设置到控件中
                }
                break;
            case R.id.btSubmit:
                baoMing();
                break;
        }
    }
}
