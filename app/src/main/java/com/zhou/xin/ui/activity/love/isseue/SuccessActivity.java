package com.zhou.xin.ui.activity.love.isseue;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.MultiItemCommonAdapter;
import com.zhou.xin.adapter.base.MultiItemTypeSupport;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.ui.activity.love.ReportActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SuccessActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SuccessActivity";
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    private MultiItemCommonAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_success;
    }

    @Override
    protected void init() {
        tv_head.setText("成功案例");
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setBackground(getResources().getDrawable(R.drawable.item_down));
        getInfo();
        initRecycle();
        refresh.setOnRefreshListener(this);
    }

    /**
     * 获取服务器数据
     */
    private void getInfo() {
        String token = App.getInstance().getUserInfo().getToken();
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();
        Request build = new Request.Builder()
                .url(Constant.GET_LIST)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(build);
        dialog.show();
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
            }
        });
    }

    @OnClick({R.id.back, R.id.iv_add})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_add:
                View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_success, null);
                final PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);// 点击back退出pop
                pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));// 设置背景透明，点击back退出pop
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        pop.dismiss();
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                pop.showAsDropDown(iv_add, 0, 80);
                inflate.findViewById(R.id.tv_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //图文
                        startToActivity(PutActivity.class);
                        pop.dismiss();
                    }
                });
                inflate.findViewById(R.id.tv_video).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //视文
                        //startToActivity(PutVideoActivity.class);
                        toVideo();
                        pop.dismiss();
                    }
                });
                inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pop.dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 使用框架寻找视频
     */
    private boolean needFull = true;
    private int width = 480;
    private int height = 480;
    private int minTime = 1000;
    private int maxTime = 10000;
    private int maxFramerate = 20;
    private int bitrate = 580000;

    private void toVideo() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .fullScreen(needFull)
                .smallVideoWidth(needFull?0:width)
                .smallVideoHeight(height)
                .recordTimeMax(maxTime)
                .recordTimeMin(minTime)
                .maxFrameRate(maxFramerate)
                .videoBitrate(bitrate)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, PutVideoActivity.class.getName(), config);
    }

    private void initRecycle() {
        //一位数为照片文字  两位数为视频  三位数文字
        List<String> data = new ArrayList<>();
        data.add("11");
        data.add("11");
        data.add("111");
        data.add("111");
        data.add("1");
        data.add("1");
        data.add("11");
        data.add("111");
        data.add("11");
        data.add("11");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("111");
        data.add("111");
        adapter = new MultiItemCommonAdapter<String>(this, data, new MultiItemTypeSupport<String>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 1) {//照片文字
                    return R.layout.view_issue_imager_text;
                } else if (itemType == 2) {
                    return R.layout.view_video;
                }
                return R.layout.view_text;
            }

            @Override
            public int getItemViewType(int position, String s) {
                if (s.length() == 3) {
                    return 3;
                }
                if (s.length() == 1) {
                    return 1;
                }
                if (s.length() == 2) {
                    return 2;
                }
                return 3;
            }
        }) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                int itemCount = getItemCount();
                if (itemCount == 1) {
                    //textImagerManger(holder,bean,position);
                } else if (itemCount == 2) {
                    //textVedioManger(holder,bean,position);
                } else {
                    //textManger(holder,bean,position);
                }
                //举报
                holder.setOnClickListener(R.id.iv_report, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startToActivity(ReportActivity.class);/***这里到时候要传参数过去*/
                        Report();
                    }
                });
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
    }

    /**
     * 举报，有参数要传，待续
     */
    private void Report() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_report, null);
        dialog.setView(inflate, 0, 0, 0, 0);
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(ReportActivity.class);//举报，有参数要传，待续
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        }, 2000);
    }
}
