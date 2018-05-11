package com.zhou.xin.ui.activity.love;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.hyphenate.util.DensityUtil;
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
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.ui.activity.love.isseue.PhotoiewerActivity;
import com.zhou.xin.ui.activity.love.isseue.SuccessActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DateUtil;
import com.zhou.xin.utils.GlideRoundTransform;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;
import com.zhuang.likeviewlibrary.LikeView;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFriendActivity extends BaseActivity {

    private static final String TAG = "MyFriendActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    private MultiItemCommonAdapter adapter;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    private BaseCommonAdapter<String> photoAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_friend;
    }

    @Override
    protected void init() {
        tv_head.setText("我的朋友圈");
        getInfo();
        initRecycle();
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

                holder.getView(R.id.iv_report).setVisibility(View.GONE);
                holder.getView(R.id.like).setVisibility(View.GONE);

                /**
                 * 删除朋友圈动态
                 */
                holder.setOnClickListener(R.id.ivDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDelete(bean.getId()+"",position);
                    }
                });

                List<String> photo = bean.getIconList();

                RecyclerView headerPhoto = holder.getView(R.id.header);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MyFriendActivity.this, 5, GridLayoutManager.VERTICAL, false);
                headerPhoto.setLayoutManager(gridLayoutManager);

                photoAdapter = new BaseCommonAdapter<String>(mContext, R.layout.header_photo_view, photo) {
                    @Override
                    public void convert(ViewHolder holder, String s, int position) {
                        CircleImageView img = holder.getView(R.id.ivPhoto);
                        String url = Constant.URL + s;
                        Glide.with(mContext).load(url).centerCrop().placeholder(R.drawable.ic_avatar).into(img);
                    }
                };
                headerPhoto.setAdapter(photoAdapter);

                CircleImageView circle = holder.getView(R.id.avatar);
                Glide.with(getApplicationContext()).load(Constant.URL + bean.getIconUrl())
                        .placeholder(R.drawable.ic_avatar)
                        .dontAnimate()
                        .into(circle);
                //circle.setOnClickListener(v -> startActivity(ObjectActivity.newIntent(getApplicationContext(),bean.getMobile())));
                holder.setText(R.id.name, bean.getNickName());
                try {
                    holder.setText(R.id.date, DateUtil.showTime(bean.getPublish_time()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(bean.getContent()))
                    holder.setText(R.id.content, bean.getContent());


                int type = getItemViewType(position);
                if (type == 1) {
                    textImagerManger(holder, bean, position);
                } else if (type == 2) {
                    textVedioManger(holder, bean, position);
                } else {
                    //textManger(holder,bean,position);
                }

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
     * 删除某条动态
     * @param talkId
     */
    private void toDelete(String talkId,int position) {
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "25";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&talkId=" + talkId + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);

        System.out.println("拼接后_t的数据--------" + joint);

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("token", token)
                .add("talkId", talkId)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(getApplicationContext(),userInfo.getMsg());
                            /*adapter.remove(position);
                            adapter.notifyDataSetChanged();*/
                            getInfo();
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(getApplicationContext(),userInfo.getMsg());
                        }
                    });
                }
                Log.d(TAG, "onResponse: "+string);

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
        JZVideoPlayerStandard jzVideoPlayerStandard = holder.getView(R.id.videoView);
        jzVideoPlayerStandard.setUp(Constant.URL + bean.getVideo_url(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(this).load(Constant.URL + bean.getVideo_mini()).into(jzVideoPlayerStandard.thumbImageView);
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
        for (int i = 0; i < tpSet.size(); i++) {
            photosList.add(tpSet.get(i).getPhotoUrl());
        }
        int num = 1;
        if (photosList.size() == 2)
            num = 2;
        if (photosList.size() >= 3)
            num = 3;
        Log.d(TAG, "所有照片的地址: " + photosList.toString());

        LinearLayoutManager layoutManager = new GridLayoutManager(this, num, GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        photos.setLayoutManager(layoutManager);
        photos.setHasFixedSize(true);
        photos.setNestedScrollingEnabled(false);
        if (photos.getAdapter() == null) {
            photos.addItemDecoration(new GridsSpacesItemDecoration(10));
        }

        photos.setAdapter(new BaseCommonAdapter<String>(this, R.layout.view_photo, photosList) {
            @Override
            public void convert(ViewHolder holder, String s, final int pos) {

                String url = Constant.URL + s;
                ImageView img = holder.getView(R.id.img);
                if (photosList.size() == 2) {
                    ViewGroup.LayoutParams layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 150));
                    img.setLayoutParams(layoutParams);
                }
                if (photosList.size() > 2) {
                    ViewGroup.LayoutParams layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 100));
                    img.setLayoutParams(layoutParams);
                }

                if (photosList.size() == 1) {
                    ViewGroup.LayoutParams layoutParams = new
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 180));
                    img.setLayoutParams(layoutParams);
                }

                Glide.with(mContext).load(url).transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 2))
                        .placeholder(R.drawable.ic_default_image)
                        .dontAnimate()
                        .into(img);

                holder.setOnClickListener(R.id.img, view -> {
                    String[] array = photosList.toArray(new String[photosList.size()]);
                    startActivity(PhotoiewerActivity.newIntent(mContext, array, pos));
                });
            }
        });
    }

    /**
     * 获取个人朋友圈列表
     */
    private void getInfo() {
        dialog.show();
        String token = App.getInstance().getUserInfo().getToken();
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("token", token).build();
        Request request = new Request.Builder().url(Constant.My_URL).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                String string = response.body().string();
                Log.d(TAG, "获取个人朋友圈列表-onResponse: "+string);
                getResult(string);
            }
        });
    }

    private void getResult(String data) {
        Gson gson = new Gson();
        TalkBean talkBean = gson.fromJson(data, TalkBean.class);
        if (talkBean.getError().equals("-1")) {
            final List<TalkBean.TalkListBean> talkList = talkBean.getTalkList();
            runOnUiThread(() -> {
                adapter.addDatas(talkList);
                adapter.notifyDataSetChanged();
            });
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
