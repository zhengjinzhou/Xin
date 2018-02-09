package com.zhou.xin.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseFragment;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.SelectBean;
import com.zhou.xin.ui.activity.love.activity.ActivityActivity;
import com.zhou.xin.ui.activity.love.DetailActivity;
import com.zhou.xin.ui.activity.love.GuildActivity;
import com.zhou.xin.ui.activity.love.InviteActivity;
import com.zhou.xin.ui.activity.love.LotteryActivity;
import com.zhou.xin.ui.activity.love.OpinionActivity;
import com.zhou.xin.ui.activity.love.SafeActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.LogUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.SpUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by zhou on 2018/1/10.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.circle) CircleImageView circle;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.iv_gender) ImageView iv_gender;
    @BindView(R.id.tv_exit) TextView tv_exit;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.tv_head) TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(View v) {
        back.setVisibility(View.GONE);
        tv_head.setText("我");
        initRecycle();
        getInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfo();
    }

    /**
     * Recycle的自定义布局
     */
    private void initRecycle() {
        ArrayList<BaseBean> data = new ArrayList<>();
        data.add(new BaseBean(SafeActivity.class,"安全管理",R.drawable.saft));
        data.add(new BaseBean(LotteryActivity.class,"抽奖活动",R.drawable.lottery));
        data.add(new BaseBean(OpinionActivity.class,"意见反馈",R.drawable.opinion));
        data.add(new BaseBean(ActivityActivity.class,"线下活动",R.drawable.activity));

        BaseCommonAdapter adapter = new BaseCommonAdapter<BaseBean>(getContext(), R.layout.recycle_me, data) {
            @Override
            public void convert(ViewHolder holder, final BaseBean baseBean, int position) {
                holder.setText(R.id.tv_name,baseBean.getName());
                holder.setImageResource(R.id.iv_icon,baseBean.getIcon());
                holder.setOnClickListener(R.id.layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startToActivity(baseBean.getaClass());
                    }
                });
            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(adapter);
    }

    /**
     * 联网获取数据
     *
     * 获取个人信息设置选项
     */
    private void getInfo() {
        if (App.getInstance().getUserInfo() == null){
            return;
        }
        String uid = App.getInstance().getUserInfo().getUid();
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "4";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + "&uid=" + uid + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("uid", uid)
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 回调
     */
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "onFailure: " + e);
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            String string = response.body().string();
            LogUtil.d("草草草哦啊从奥次哦啊从初次："+string);
            Gson gson = new Gson();
            SelectBean selectBean = gson.fromJson(string, SelectBean.class);
            App.getInstance().setSelectBean(selectBean);

        }
    };

    private void setInfo() {
        PersonalBean personalBean = App.getInstance().getPersonalBean();
        if (personalBean == null)
            return;
        if (TextUtils.isEmpty(personalBean.getMemInfo().getNickname())) {
            tv_name.setText("");
        }
        if (personalBean.getMemInfo().getGender() == 0) {
            iv_gender.setImageResource(R.drawable.female);
        }
        iv_gender.setImageResource(R.drawable.male);
        tv_name.setText(personalBean.getMemInfo().getNickname());
        Glide.with(getContext()).load(Constant.URL + personalBean.getMemInfo().getPhotoPath()).into(circle);
        SpUtil.putString(getContext(),Constant.APP_PHOTO,Constant.URL + personalBean.getMemInfo().getPhotoPath());
    }

    @OnClick({ R.id.tv_edit, R.id.tv_exit,  R.id.tv_help,R.id.tv_invite})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                startToActivity(DetailActivity.class);
                break;
            case R.id.tv_exit:
                showPopupWindow();
                break;
            case R.id.tv_help:
                startToActivity(GuildActivity.class);
                break;
            case R.id.tv_invite:
                startToActivity(InviteActivity.class);
                break;
        }
    }

    private void showPopupWindow() {
        View inflate = getLayoutInflater().inflate(R.layout.popupwindow, null);
        final PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);// 点击back退出pop
        pop.setAnimationStyle(R.style.add_new_style);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));// 设置背景透明，点击back退出pop
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                pop.dismiss();
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.showAtLocation(tv_exit, Gravity.BOTTOM, 0, 0);
        inflate.findViewById(R.id.tv_ensure_log_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtil.clear();
                pop.dismiss();
                getActivity().finish();
            }
        });
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }

    public class BaseBean{
        Class aClass;
        String name;
        int icon;

        public BaseBean(Class aClass, String name, int icon) {
            this.aClass = aClass;
            this.name = name;
            this.icon = icon;
        }

        public Class getaClass() {
            return aClass;
        }

        public void setaClass(Class aClass) {
            this.aClass = aClass;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}
