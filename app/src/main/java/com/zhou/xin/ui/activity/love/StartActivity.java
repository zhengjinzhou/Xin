package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.zhou.xin.BuildConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.UpdateBean;
import com.zhou.xin.index.IndexContract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartActivity extends BaseActivity implements IndexContract.View{

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<View> list;
    private SharedPreferences sp;
    int versionCode = BuildConfig.VERSION_CODE;

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        getUpate();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sp = getSharedPreferences("Start", Context.MODE_PRIVATE);

    }

    private void getUpate() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Constant.UPDATE_NEW).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("----版本更新问题-----", "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("------版本更新问题----", "onResponse: "+string);
                Gson gson = new Gson();
                UpdateBean updateBean = gson.fromJson(string, UpdateBean.class);
                if (updateBean.getVersion().getId() > versionCode){
                    runOnUiThread(() -> {
                        //显示更新界面
                        updateApp();
                    });
                }else {
                    runOnUiThread(() -> {
                        //正常进入系统
                        if (sp.getBoolean("FIRST", false)){
                            startToActivity(SplashActivity.class);
                            finish();
                        }else {
                            initViewpager();
                        }
                    });
                }
            }
        });
    }

    /**
     * 更新app
     */
    private void updateApp() {

    }


    private void initViewpager() {
        viewPager = findViewById(R.id.viewPager);
        list = new ArrayList<>();
        View view1 = getLayoutInflater().inflate(R.layout.layout1, null);
        View view2 = getLayoutInflater().inflate(R.layout.layout2, null);
        View view3 = getLayoutInflater().inflate(R.layout.layout3, null);

        view3.findViewById(R.id.bt_next).setOnClickListener(v -> {
            startToActivity(AppActivity.class);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("FIRST",true);
            edit.commit();
            finish();
        });

        list.add(view1);
        list.add(view2);
        list.add(view3);

        viewPager.setAdapter(new MyPagerAdapter());
    }

    @Override
    public void showUpdate(String version) {

    }

    @Override
    public void showProgress(int progress) {

    }

    @Override
    public void showFail(String msg) {

    }

    @Override
    public void showComplete(File file) {

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }
}
