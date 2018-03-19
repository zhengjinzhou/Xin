package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;
import com.zhou.xin.widget.Xcircleindicator;

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

/**
 * 对方的信息
 */
public class ObjectActivity extends BaseActivity {

    private static final String TAG = "ObjectActivity";
    public static Intent newIntent(Context context, String mobile){
        Intent intent = new Intent(context,ObjectActivity.class);
        intent.putExtra("mobile", mobile);
        return intent;
    }

    private List<String> photoUrlList = new ArrayList<>();
    private List<View> list_view = new ArrayList<>();
    @BindView(R.id.tv_username) TextView tv_username;
    @BindView(R.id.tv_province) TextView tv_province;
    @BindView(R.id.tv_city) TextView tv_city;
    @BindView(R.id.tv_constellation) TextView tv_constellation;
    @BindView(R.id.tv_major) TextView tv_major;
    @BindView(R.id.tv_labels) TextView tv_labels;
    @BindView(R.id.tv_sports) TextView tv_sports;
    @BindView(R.id.tv_musics) TextView tv_musics;
    @BindView(R.id.tv_foods) TextView tv_foods;
    @BindView(R.id.tv_films) TextView tv_films;
    @BindView(R.id.tv_books) TextView tv_books;
    @BindView(R.id.tv_travels) TextView tv_travels;
    @BindView(R.id.list_pager) ViewPager list_pager;
    @BindView(R.id.Xcircleindicator) Xcircleindicator mXcircleindicator;
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.tv_title) TextView tv_title;


    @Override
    protected int getLayout() {
        return R.layout.activity_object;
    }

    @Override
    protected void init() {
        String fmobile = getIntent().getStringExtra("mobile");
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "20";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&fmobile=" + fmobile + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("fmobile", fmobile)
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, final IOException e) {
            runOnUiThread(() -> ToastUtil.show(getApplicationContext(), e.getMessage()));
            Log.d(TAG, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();
            Log.d(TAG, "获取好友个人信息成功: " + string);
            getResult(string);
        }
    };

    /**
     * 展示数据
     * @param data
     */
    private void getResult(String data) {
        Gson gson = new Gson();
        PersonalBean personalBean = gson.fromJson(data, PersonalBean.class);
        Log.d(TAG, "getResult: "+personalBean.getMemInfo().toString());
        if (personalBean==null){
            return;
        }
        List<PersonalBean.MemInfoBean.PicturesBean> pictures = personalBean.getMemInfo().getPictures();
        for (int i=0;i<pictures.size();i++){
            photoUrlList.add(pictures.get(i).getPhotoUrl());
            System.out.println(photoUrlList.get(i));
        }
        runOnUiThread(() -> {
            tv_head.setText(TextUtils.isEmpty(personalBean.getMemInfo().getNickname())?"":personalBean.getMemInfo().getNickname());
            tv_username.setText(TextUtils.isEmpty(personalBean.getMemInfo().getRealname())?"":personalBean.getMemInfo().getRealname());
            tv_province.setText(TextUtils.isEmpty(personalBean.getMemInfo().getCity().getProvince().getName())?"":personalBean.getMemInfo().getCity().getProvince().getName());
            tv_city.setText(TextUtils.isEmpty(personalBean.getMemInfo().getCity().getName())?"":personalBean.getMemInfo().getCity().getName());
            tv_constellation.setText(TextUtils.isEmpty(personalBean.getMemInfo().getConstellation())?"":personalBean.getMemInfo().getConstellation());
            tv_major.setText(TextUtils.isEmpty(personalBean.getMemInfo().getMajor().getMajorName())?"":personalBean.getMemInfo().getMajor().getMajorName());
            tv_labels.setText(TextUtils.isEmpty(personalBean.getMemInfo().getLabels())?"":personalBean.getMemInfo().getLabels());
            tv_sports.setText(TextUtils.isEmpty(personalBean.getMemInfo().getSports())?"":personalBean.getMemInfo().getSports());
            tv_musics.setText(TextUtils.isEmpty(personalBean.getMemInfo().getMusic())?"":personalBean.getMemInfo().getMusic());
            tv_foods.setText(TextUtils.isEmpty(personalBean.getMemInfo().getFoods())?"":personalBean.getMemInfo().getFoods());
            tv_films.setText(TextUtils.isEmpty(personalBean.getMemInfo().getFilms())?"":personalBean.getMemInfo().getFilms());
            tv_books.setText(TextUtils.isEmpty(personalBean.getMemInfo().getBooks())?"":personalBean.getMemInfo().getBooks());
            tv_travels.setText(TextUtils.isEmpty(personalBean.getMemInfo().getTravels())?"":personalBean.getMemInfo().getTravels());
            setPhoto();
        });
    }

    @OnClick({R.id.back}) void onClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void setPhoto() {
        list_view = new ArrayList<>();
        for (int i=0;i<photoUrlList.size();i++){
            LayoutInflater inflater = getLayoutInflater();
            View inflate = inflater.inflate(R.layout.fragment_page, null);
            ImageView iv_image = inflate.findViewById(R.id.iv_image);
            Glide.with(getApplicationContext()).load(Constant.URL+photoUrlList.get(i)).into(iv_image);
            list_view.add(inflate);
        }
        list_pager.setAdapter(new MypageAdapter());
        //设置总共的页数
        mXcircleindicator.initData(photoUrlList.size(), 0);
        //设置当前的页面
        mXcircleindicator.setCurrentPage(0);
        list_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mXcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * PagerAdapter 适配器
     */
    public class MypageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list_view.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list_view.get(position));
            return list_view.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list_view.get(position));
        }
    }
}
