package com.zhou.xin.ui.activity.love.isseue;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.hyphenate.util.DensityUtil;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.GridsSpacesItemDecoration;
import com.zhou.xin.adapter.base.MultiItemCommonAdapter;
import com.zhou.xin.adapter.base.MultiItemTypeSupport;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.TalkBean;
import com.zhou.xin.ui.activity.love.ReportActivity;
import com.zhou.xin.ui.activity.love.ReportInfoActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DateUtil;
import com.zhou.xin.utils.GlideRoundTransform;
import com.zhou.xin.utils.LogUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.PersonalFormTools;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;
import com.zhou.xin.utils.VideoUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SuccessActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SuccessActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;
    @BindView(R.id.iv_add) ImageView iv_add;
    private MultiItemCommonAdapter adapter;
    private boolean aBoolean = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_success;
    }

    @Override
    protected void init() {
        tv_head.setText(R.string.SuccessActivity);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setBackground(getResources().getDrawable(R.drawable.more_unfold));
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
                LogUtil.d("-----------朋友圈信息--------------" + string);
                getResult(string);
            }
        });
    }

    /**
     * 朋友圈信息
     *
     * @param data
     */
    private void getResult(String data) {
        Gson gson = new Gson();
        TalkBean talkBean = gson.fromJson(data, TalkBean.class);
        if (talkBean.getError().equals("-1")) {
            final List<TalkBean.TalkListBean> talkList = talkBean.getTalkList();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.addDatas(talkList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
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
                        startToActivity(PutActivity.class);//图文
                        pop.dismiss();
                    }
                });
                inflate.findViewById(R.id.tv_video).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        VideoUtil.toVideo(SuccessActivity.this);    //视文
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

    private void initRecycle() {
        //一位数为照片文字  两位数为视频  三位数文字
        List<TalkBean.TalkListBean> data = new ArrayList<>();
        adapter = new MultiItemCommonAdapter<TalkBean.TalkListBean>(this, data, new MultiItemTypeSupport<TalkBean.TalkListBean>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 1) {//照片文字
                    return R.layout.view_issue_imager_text;
                } else if (itemType == 2) {//视文
                    return R.layout.view_video;
                }
                return R.layout.view_text;
            }

            @Override
            public int getItemViewType(int position, TalkBean.TalkListBean s) {
                if (s.getCode() == 3) {
                    return 3;//纯文
                }
                if (s.getCode() == 1) {
                    return 1;//图文
                }
                if (s.getCode() == 2) {
                    return 2;//视文
                }
                return 3;
            }
        }) {
            @Override
            public void convert(final ViewHolder holder, final TalkBean.TalkListBean bean, int position) {
                CircleImageView circle = holder.getView(R.id.avatar);
                Glide.with(getApplicationContext()).load(Constant.URL + bean.getIconUrl())
                        .placeholder(R.drawable.ic_avatar)
                        .dontAnimate()
                        .into(circle);
                holder.setText(R.id.name, bean.getNickName());
                try {
                    holder.setText(R.id.date, DateUtil.showTime(bean.getPublish_time()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(bean.getContent()))
                    holder.setText(R.id.content, bean.getContent());
                holder.setText(R.id.like,bean.getTapTimes()+"");
                int type = getItemViewType(position);
                if (type == 1) {
                    textImagerManger(holder, bean, position);
                } else if (type == 2) {
                    textVedioManger(holder, bean, position);
                } else {
                    //textManger(holder,bean,position);
                }
                //举报
                holder.setOnClickListener(R.id.iv_report, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Report(bean.getMobile());
                    }
                });
                PointLike(holder, bean); //点赞
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
     * 点赞
     *
     * @param holder
     * @param bean
     */
    private void PointLike(final ViewHolder holder, final TalkBean.TalkListBean bean) {
        holder.setOnClickListener(R.id.like, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aBoolean){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 取消");
                            holder.setText(R.id.like, bean.getTapTimes()+"");
                            Drawable drawable = getResources().getDrawable(R.drawable.issue_like);// 找到资源图片
                            // 这一步必须要做，否则不会显示。
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                            ((TextView)holder.getView(R.id.like)).setCompoundDrawables(drawable, null, null, null);// 设置到控件中
                            toSend("cancel",bean.getId()+"");
                            aBoolean = false;
                        }
                    });
                    return;
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 点赞");
                            int num = bean.getTapTimes() + 1;
                            holder.setText(R.id.like,num+"");
                            Drawable drawable = getResources().getDrawable(R.drawable.issue_like_point);// 找到资源图片
                            // 这一步必须要做，否则不会显示。
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                            ((TextView)holder.getView(R.id.like)).setCompoundDrawables(drawable, null, null, null);// 设置到控件中
                            toSend("focus",bean.getId()+"");
                            aBoolean = true;
                        }
                    });
                }
            }
        });
    }

    /**
     * 点赞接口
     * @param action
     */
    private void toSend(String action,String talkId) {
        Log.d(TAG, "toSend: "+action+","+talkId);
        String opt = "16";
        String token = App.getInstance().getUserInfo().getToken();
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&action=" + action + "&opt=" + opt + "&talkId=" + talkId +"&token="+token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        System.out.println("拼接后_t的数据--------" + joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("opt", opt)
                .add("token", token)
                .add("action", action)
                .add("talkId", talkId)
                .add("_t", _t)
                .add("_s", _s)
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
                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
    }

    /**
     * 视频文字
     *
     * @param holder
     * @param bean
     * @param position
     */
    private void textVedioManger(ViewHolder holder, TalkBean.TalkListBean bean, int position) {
        JZVideoPlayerStandard jzVideoPlayerStandard =  holder.getView(R.id.videoView);
        jzVideoPlayerStandard.setUp(Constant.URL+bean.getVideo_url(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(this).load(Constant.URL+bean.getVideo_mini()).into(jzVideoPlayerStandard.thumbImageView);
    }

    /**
     * 图文朋友圈展示
     *
     * @param holder
     * @param bean
     * @param position
     */
    private void textImagerManger(ViewHolder holder, TalkBean.TalkListBean bean, int position) {

        RecyclerView photos = holder.getView(R.id.photosList);

        List<TalkBean.TalkListBean.TpSetBean> tpSet = bean.getTpSet();
        final List<String> photosList = new ArrayList<>();
        for (int i=0;i<tpSet.size();i++){
            photosList.add(tpSet.get(i).getPhotoUrl());
        }
        int num = 1;
        if (photosList.size() == 2)
            num = 2;
        if (photosList.size() == 3)
            num = 3;
        Log.d(TAG, "所有照片的地址: "+photosList.toString());

        GridLayoutManager layoutManager = new GridLayoutManager(this,num,GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        photos.setLayoutManager(layoutManager);
        photos.setHasFixedSize(true);
        photos.setNestedScrollingEnabled(false);
        if(photos.getAdapter() == null){
            photos.addItemDecoration(new GridsSpacesItemDecoration(10));
        }

        photos.setAdapter(new BaseCommonAdapter<String>(this,R.layout.view_photo,photosList) {
            @Override
            public void convert(ViewHolder holder, String s, final int pos) {

                String url = Constant.URL + s;
                ImageView img = holder.getView(R.id.img);
                if(photosList.size() == 2){
                    ViewGroup.LayoutParams  layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext,150));
                    img.setLayoutParams(layoutParams);
                }
                if(photosList.size() > 2){
                    ViewGroup.LayoutParams  layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext,100));
                    img.setLayoutParams(layoutParams);
                }

                if(photosList.size() == 1){
                    ViewGroup.LayoutParams  layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext,180));
                    img.setLayoutParams(layoutParams);
                }

                Glide.with(mContext).load(url).transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,2))
                        .placeholder(R.drawable.ic_default_image)
                        .dontAnimate()
                        .into(img);

                holder.setOnClickListener(R.id.img, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] array = photosList.toArray(new String[photosList.size()]);
                        // Toast.makeText(mContext, "" + pos, Toast.LENGTH_SHORT).show();
                        startActivity(PhotoiewerActivity.newIntent(mContext,array,pos));
                    }
                });
            }
        });
    }

    /**
     * 举报，有参数要传，待续
     */
    private void Report(final String mobile) {
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
                startActivity(ReportActivity.newIntent(getApplicationContext(),mobile));//传递举报手机号码过去
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
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

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
