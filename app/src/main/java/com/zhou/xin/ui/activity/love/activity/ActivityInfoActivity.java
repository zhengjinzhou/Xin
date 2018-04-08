package com.zhou.xin.ui.activity.love.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zhou.xin.ui.activity.love.LotteryActivity;
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
    @BindView(R.id.tv_show) TextView tv_show;
    @BindView(R.id.tv_message) TextView tv_message;
    @BindView(R.id.main_backdrop) ImageView main_backdrop;
    @BindView(R.id.tvHead) TextView tvHead;
    @BindView(R.id.tvPlace) TextView tvPlace;
    @BindView(R.id.tvStart) TextView tvStart;
    @BindView(R.id.tvEnd) TextView tvEnd;
    @BindView(R.id.tv_tip) TextView tv_tip;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tvPhoneNumber) TextView tvPhoneNumber;
    @BindView(R.id.tvMoney) TextView tvMoney;

    boolean isShow = false;
    private CommonAdapter adapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void init() {
        initRecycler();
        getBaoming();
        setInfo();
    }

    private void initRecycler() {
        List<BaomingBean.MemberListBean> data = new ArrayList<>();
        adapter = new CommonAdapter<BaomingBean.MemberListBean>(this, R.layout.item_activity, data) {
            @Override
            public void convert(ViewHolder holder, BaomingBean.MemberListBean s, int position) {
                CircleImageView img = holder.getView(R.id.circle);
                Glide.with(getApplicationContext()).load(Constant.URL + s.getUrl()).into(img);
                holder.setText(R.id.tvName, s.getMemberName());
                holder.setText(R.id.tvAge, s.getAge() + "岁");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取报名者
     */
    private void getBaoming() {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "24";
        String activityId = App.getInstance().getActivityBean().getId() + "";
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
                Log.d(TAG, "onFailure: 获取报名者 " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: 获取报名者" + string);
                Gson gson = new Gson();
                BaomingBean baomingBean = gson.fromJson(string, BaomingBean.class);
                List<BaomingBean.MemberListBean> memberList = baomingBean.getMemberList();
                runOnUiThread(() -> {
                    adapter.clear();
                    adapter.addDatas(memberList);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void setInfo() {
        ActivityBean.ActivityListBean activityList = App.getInstance().getActivityBean();
        Glide.with(this).load(Constant.URL + activityList.getPhotoUrl()).into(main_backdrop);
        tvHead.setText(activityList.getActivityName());
        tvPlace.setText(activityList.getPlace());
        tvStart.setText(activityList.getStartTime());
        tvEnd.setText("-" + activityList.getEndTime());
        tv_message.setText(activityList.getActivityDetail());
        tv_tip.setText(activityList.getReminder());
        tvPhoneNumber.setText(activityList.getMobile());
        tvMoney.setText(activityList.getActivityFee());
    }

    /**
     * 报名参与
     */
    private void baoMing() {
        String token = App.getInstance().getUserInfo().getToken();
        String mobile = App.getInstance().getUserInfo().getAccountNumber();
        String opt = "23";
        String activityId = App.getInstance().getActivityBean().getId() + "";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&activityId=" + activityId + "&mobile=" + mobile + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        Log.d(TAG, "getInfo: " + joint);
        String _s = Md5Util.encoder(joint);
        dialog.show();
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
                dialog.dismiss();
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")) {
                    //抽奖
                    toLottery();
                    //成功之后更新头像
                    getBaoming();
                } else {
                    runOnUiThread(() -> ToastUtil.show(getApplicationContext(), userInfo.getMsg()));
                }
            }
        });
    }

    /**
     * 抽奖
     */
    private void toLottery() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("报名成功！");
        builder.setTitle("恭喜您获得一次抽奖机会，是否参与抽奖？");
        builder.setNegativeButton("否", (dialog, which) -> dialog.dismiss()).setPositiveButton("是", (dialog, which) -> {
            dialog.dismiss();
            startToActivity(LotteryActivity.class);
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @OnClick({R.id.tv_show, R.id.btSubmit, R.id.tvPhoneNumber, R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvPhoneNumber:
                // 检查是否获得了权限（Android6.0运行时权限）
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                111);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
                break;
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

    /**
     * 拨打电话
     */
    private void CallPhone() {
        String number = tvPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            // 提醒用户
            // 注意：在这个匿名内部类中如果用this则表示是View.OnClickListener类的对象，
            // 所以必须用MainActivity.this来指定上下文环境。
            Toast.makeText(this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + number); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
}
