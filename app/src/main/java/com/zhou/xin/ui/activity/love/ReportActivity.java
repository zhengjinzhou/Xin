package com.zhou.xin.ui.activity.love;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.ReportBean;
import com.zhou.xin.ui.activity.huanxin.MainActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.io.Serializable;
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
 * 用户举报
 */
public class ReportActivity extends BaseActivity {

    private static final String TAG = "ReportActivity";
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.tv_head) TextView tv_head;
    private List<ReportBean.AccusationCategoryListBean> data;
    private CommonAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_report;
    }

    /**
     * 传递手机号码
     *
     * @param context
     * @param mobile
     * @return
     */
    public static Intent newIntent(Context context, String mobile) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra("mobile", mobile);
        return intent;
    }

    @Override
    protected void init() {

        tv_head.setText("举报用户");
        getInfo();
        initRecycle();

    }

    private void initRecycle() {
        data = new ArrayList<>();
        adapter = new CommonAdapter<ReportBean.AccusationCategoryListBean>(this, R.layout.recycle_guild, data) {
            @Override
            public void convert(ViewHolder holder, final ReportBean.AccusationCategoryListBean s, int position) {
                holder.setText(R.id.tv_des,s.getCategoryName());
                holder.setOnClickListener(R.id.rl_guild, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (s.getAcTypes().size() == 0){
                            startActivity(ReportInfoActivity.newIntent(getApplicationContext(),s.getId()+"",getIntent().getStringExtra("username"),getIntent().getStringExtra("mobile")));
                            Log.d(TAG, "onClick: "+s.getId());
                        }else {
                            List<ReportBean.AccusationCategoryListBean.AcTypesBean> acTypes = s.getAcTypes();
                            String json = new Gson().toJson(acTypes);
                            startActivity(Report2Activity.newintent(getApplicationContext(),json,getIntent().getStringExtra("username"),getIntent().getStringExtra("mobile")));
                        }
                    }
                });
            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    /**
     * 获取举报类型
     */
    private void getInfo() {
        String token = App.getInstence().getUserInfo().getToken();
        String opt = "13";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);

        dialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(body).build();
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
                getResult(string);
            }
        });
    }

    /**
     * 接口的回调方法
     * @param data
     */
    private void getResult(String data) {
        Gson gson = new Gson();
        ReportBean reportBean = gson.fromJson(data, ReportBean.class);
        final List<ReportBean.AccusationCategoryListBean> categoryListBeans = reportBean.getAccusationCategoryList();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addDatas(categoryListBeans);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.dismiss();
    }

    @OnClick({R.id.tv_report,R.id.back}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_report:
                startToActivity(TextActivity.class);
                break;
        }
    }
}
